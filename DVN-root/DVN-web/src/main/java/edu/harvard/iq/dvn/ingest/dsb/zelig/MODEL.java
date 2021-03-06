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
package edu.harvard.iq.dvn.ingest.dsb.zelig;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;


/**
 * <p>Java class for MODEL.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MODEL">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="continuous"/>
 *     &lt;enumeration value="discrete"/>
 *     &lt;enumeration value="nominal"/>
 *     &lt;enumeration value="ordinal"/>
 *     &lt;enumeration value="binary"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlEnum
public enum MODEL {

    @XmlEnumValue("continuous")
    CONTINUOUS("continuous"),
    @XmlEnumValue("discrete")
    DISCRETE("discrete"),
    @XmlEnumValue("nominal")
    NOMINAL("nominal"),
    @XmlEnumValue("ordinal")
    ORDINAL("ordinal"),
    @XmlEnumValue("binary")
    BINARY("binary");
    private final String value;

    MODEL(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MODEL fromValue(String v) {
        for (MODEL c: MODEL.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
