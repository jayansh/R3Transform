/*
 * Copyright 2002,2013 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.commons.jelly.tags.core;

import com.converter.premigration.Xcipher;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;

import java.net.URI;

import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.JellyTagException;
import org.apache.commons.jelly.MissingAttributeException;
import org.apache.commons.jelly.TagSupport;
import org.apache.commons.jelly.XMLOutput;

import org.xml.sax.InputSource;

/** Imports another script.
 *
 *  <p>
 *  By default, the imported script does not have access to
 *  the parent script's variable context.  This behaviour
 *  may be modified using the <code>inherit</code> attribute.
 *  </p>
 *
 * @author <a href="mailto:bob@eng.werken.com">bob mcwhirter</a>
 * @version $Revision: 155420 $
 */

public class ImportTag extends TagSupport {

    /**
     * the location of the script being imported, relative to the
     * current script
     */
    private String uri;

    /**
     * Whether the imported script has access to the caller's variables
     */
    private boolean inherit;

    /**
     * The file to be imported. Mutually exclusive with uri.
     * uri takes precedence.
     */
    private String file;

    /**
     * Create a new Import tag.
     */
    public ImportTag() {
    }


    // Tag interface
    //-------------------------------------------------------------------------
    /**
     * Perform tag processing
     * @param output the destination for output
     * @throws MissingAttributeException if a required attribute is missing
     * @throws JellyTagException on any other errors
     */
    public void doTag(XMLOutput output) throws MissingAttributeException, JellyTagException {
        String encodedString;
        InputStream inputStream = null;
        InputSource inputSource = null;

        if (uri == null && file == null) {
            throw new MissingAttributeException( "uri" );
        }

        try {
            if (uri != null) {
                encodedString = uri.toString();
            } else {
                encodedString = file;
            }
            //Replaced the .r3 to .rrr by shelli
        encodedString = encodedString.replaceAll(".xml", ".r3");
            //encodedString = encodedString.replaceAll(".xml", ".rrr");

            File encodedFile = new File(encodedString);
            String s=null;
            try {

                FileReader fr = new FileReader(encodedFile);

                BufferedReader br = new BufferedReader(fr);
                s = br.readLine();

                Xcipher c = new Xcipher();
                s = c.DecryptCode(s, 0, 0);
                ByteArrayInputStream in = 
                    new ByteArrayInputStream(s.getBytes());
                inputStream = (InputStream)in;

            } catch (Exception e) {
                try {
                    inputStream = new FileInputStream(new File(file));
                } catch (Exception f) {
                    // TODO
                    f.printStackTrace();
                }
            }
            inputSource = new InputSource(inputStream);
            context.runScript(inputSource, output, true, isInherit() );

        }
        catch (JellyException e) {
            throw new JellyTagException("could not import script",e);
        }
    }

    // Properties
    //-------------------------------------------------------------------------

    /**
     * @return whether property inheritence is enabled
     */
    public boolean isInherit() {
        return inherit;
    }

    /**
     * Sets whether property inheritence is enabled or disabled
     */
    public void setInherit(boolean inherit) {
        this.inherit = inherit;
    }

    /**
     * Sets the URI (relative URI or absolute URL) for the script to evaluate.
     */
    public void setUri(String uri) {
        this.uri = uri;
    }


    /**
     * Sets the file for the script to evaluate.
     * @param file The file to set
     */
    public void setFile(String file) {
        this.file = file;
    }

}
