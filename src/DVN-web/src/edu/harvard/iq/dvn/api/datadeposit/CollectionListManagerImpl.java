/*
 Copyright (C) 2005-2012, by the President and Fellows of Harvard College.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 Dataverse Network - A web application to share, preserve and analyze research data.
 Developed at the Institute for Quantitative Social Science, Harvard University.
 Version 3.0.
 */
package edu.harvard.iq.dvn.api.datadeposit;

import edu.harvard.iq.dvn.core.admin.VDCUser;
import edu.harvard.iq.dvn.core.study.Study;
import edu.harvard.iq.dvn.core.vdc.VDC;
import edu.harvard.iq.dvn.core.vdc.VDCServiceLocal;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Inject;
import org.apache.abdera.Abdera;
import org.apache.abdera.i18n.iri.IRI;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.swordapp.server.AuthCredentials;
import org.swordapp.server.CollectionListManager;
import org.swordapp.server.SwordAuthException;
import org.swordapp.server.SwordConfiguration;
import org.swordapp.server.SwordError;
import org.swordapp.server.SwordServerException;

public class CollectionListManagerImpl implements CollectionListManager {

    private static final Logger logger = Logger.getLogger(CollectionListManagerImpl.class.getCanonicalName());
    @EJB
    VDCServiceLocal vdcService;
    @Inject
    SwordAuth swordAuth;
    @Inject
    UrlManager urlManager;

    @Override
    public Feed listCollectionContents(IRI iri, AuthCredentials authCredentials, SwordConfiguration swordConfiguration) throws SwordServerException, SwordAuthException, SwordError {
        VDCUser vdcUser = swordAuth.auth(authCredentials);

        urlManager.processUrl(iri.toString());
        String dvAlias = urlManager.getTargetIdentifier();
        if (urlManager.getTargetType().equals("dataverse") && dvAlias != null) {

            VDC dv = vdcService.findByAlias(dvAlias);

            if (dv != null) {
                boolean authorized = false;
                List<VDC> userVDCs = vdcService.getUserVDCs(vdcUser.getId());
                for (VDC userVdc : userVDCs) {
                    if (userVdc.equals(dv)) {
                        authorized = true;
                        break;
                    }
                }
                if (!authorized) {
                    throw new SwordServerException("user " + vdcUser.getUserName() + " is not authorized to list studies in dataverse " + dv.getAlias());
                }
                Abdera abdera = new Abdera();
                Feed feed = abdera.newFeed();
                feed.setTitle(dv.getName());
                Collection<Study> studies = dv.getOwnedStudies();
                String hostName = System.getProperty("dvn.inetAddress");
                String optionalPort = "";
                int port = iri.getPort();
                if (port != -1) {
                    optionalPort = ":" + port;
                }
                String baseUrl = "https://" + hostName + optionalPort + "/dvn/api/data-deposit/v1/swordv2/";
                for (Study study : studies) {
                    Entry entry = feed.addEntry();
                    entry.setId(study.getGlobalId());
                    entry.setTitle(study.getLatestVersion().getMetadata().getTitle());
                    entry.setBaseUri(new IRI(baseUrl + "edit/" + study.getGlobalId()));
                    feed.addEntry(entry);
                }
                return feed;
            } else {
                throw new SwordServerException("Could not find dataverse: " + dvAlias);
            }
        } else {
            throw new SwordError("Couldn't determine target type or identifer from url: " + iri);
        }
    }
}