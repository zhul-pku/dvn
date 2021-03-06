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
package edu.harvard.iq.dvn.api.entities;

import java.io.File; 
import java.util.List;
import java.util.ArrayList;

import edu.harvard.iq.dvn.core.study.StudyFile;
import edu.harvard.iq.dvn.core.web.dataaccess.OptionalAccessService;


/**
 *
 * @author leonidandreev
 */
public class DownloadInfo {
    
    private StudyFile studyFile; 
    //private String mimeType; 
    
    private String authUserName = "";
    private String authMethod = "anonymous"; 
    
    Boolean accessGranted = false; 
    
    Boolean accessPermissionsApply = false; 
    Boolean accessRestrictionsApply = false;
    Boolean passAccessPermissions = false; 
    Boolean passAccessRestrictions = false; 

    private List<OptionalAccessService> optionalServicesAvailable; 
    
    public DownloadInfo(StudyFile sf) {
        studyFile = sf;
        optionalServicesAvailable = new ArrayList<OptionalAccessService>();       
    }

    public StudyFile getStudyFile() {
        return studyFile; 
    }
     
    public void setStudyFile (StudyFile sf) {
        studyFile = sf; 
    }
    
    public String getAuthUserName() {
        return authUserName; 
    }
    
    public void setAuthUserName(String un) {
        authUserName = un; 
    }
    
    public String getAuthMethod() {
        return authMethod; 
    }
    
    public void setAuthMethod(String am) {
        authMethod = am; 
    }
    
    public Boolean isPassAccessPermissions() {
        return passAccessPermissions;
    }
    
    public void setPassAccessPermissions(Boolean pass) {
        passAccessPermissions = pass; 
    }
    
    public Boolean isPassAccessRestrictions() {
        return passAccessRestrictions;
    }
    
    public void setPassAccessRestrictions(Boolean pass) {
        passAccessRestrictions = pass; 
    }
    
    public Boolean isAccessPermissionsApply() {
        return accessPermissionsApply;
    }
    
    public void setAccessPermissionsApply(Boolean pass) {
        accessPermissionsApply = pass; 
    }
    
    public Boolean isAccessRestrictionsApply() {
        return accessRestrictionsApply;
    }
    
    public void setAccessRestrictionsAply(Boolean pass) {
        accessRestrictionsApply = pass; 
    }
    
    public Boolean isAccessGranted() {
        return (passAccessPermissions && passAccessRestrictions); 
    }
    
    public String getMimeType() {
        String mType = null; 
        
        if (studyFile != null) {
            mType = studyFile.getFileType(); 
        }
        
        return mType; 
    }
    
    public Long getStudyFileId() {
        Long sfId = null; 
        
        if (studyFile != null) {
            sfId = studyFile.getId(); 
        }
        
        return sfId; 
    }
    
    public String getFileName() {
        if (studyFile != null) {
            return studyFile.getFileName();
        }
        
        return null; 
    }
    
    public Long getFileSize() {
        Long fileSize = null; 
        
        if (studyFile != null) {
            if (!studyFile.isRemote()) {
                try {
                    File tmpFile = new File (studyFile.getFileSystemLocation());
                    if (tmpFile != null) {
                        fileSize = tmpFile.length();
                    }
                } catch (Exception ex) {
                    return null; 
                }
            }
        }
        
        return fileSize; 
    }
    
    public List<OptionalAccessService> getServicesAvailable() {
	return optionalServicesAvailable; 
    }
    
    public void addServiceAvailable(OptionalAccessService accessService) {
        this.optionalServicesAvailable.add(accessService);
    }
    
}