

/*
 * Copyright 2002,2004 The Apache Software Foundation.
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

package org.apache.commons.jelly.task;

import com.converter.premigration.Xcipher;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import org.apache.commons.jelly.Jelly;
import org.apache.commons.jelly.JellyContext;
import org.apache.commons.jelly.JellyException;
import org.apache.commons.jelly.Script;
import org.apache.commons.jelly.XMLOutput;
import org.apache.commons.jelly.parser.XMLParser;
import org.apache.commons.jelly.tags.ant.AntTagLibrary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * <p><code>JellyTask</code> is an Ant task which will
 * run a given Jelly script.
 *
 * @author <a href="mailto:jstrachan@apache.org">James Strachan</a>
 * @version $Revision$
 */
public class

JellyTask extends Task {

    /** The Log to which logging calls will be made. */
    private static final Log log = LogFactory.getLog(Jelly.class);

    /** The JellyContext to use */
    private JellyContext context;

    /** The URL of the script to execute */
    private URL url;

    /** The URL of the root context for other scripts */
    private URL rootContext;

    /** The XML output */
    private XMLOutput xmlOutput;

    /** The file where output is going */
    private File output;

    // Task interface
    //-------------------------------------------------------------------------

    /**
     * Excutes the Jelly script
     */
    public void execute() throws BuildException {

        try {
            log("Running script: " + getUrl());
            if (output != null) {
                log("Sending output to: " + output);
            }

            Script script = compileScript();
            JellyContext context = getJellyContext();
            context.setVariable("project", getProject());
            script.run(context, getXMLOutput());
            getXMLOutput().flush();
        } catch (Exception e) {
            throw new BuildException(e, getLocation());
        }
    }

    // Properties
    //-------------------------------------------------------------------------

    /**
     * Sets the script URL to use as an absolute URL or a relative filename
     */
    public void setScript(String script) throws MalformedURLException {
        setUrl(resolveURL(script));
    }

    public URL getUrl() {
        return url;
    }

    /**
     * Sets the script URL to use
     */
    public void setUrl(URL url) {
        this.url = url;
    }

    /**
     * Sets the script file to use
     */
    public void setFile(File file) throws MalformedURLException {
        setUrl(file.toURL());
    }

    /**
     * Sets the output to generate
     */
    public void setOutput(File output) throws IOException {
        this.output = output;
        xmlOutput = XMLOutput.createXMLOutput(new FileWriter(output));
    }

    public XMLOutput getXMLOutput() throws IOException {
        if (xmlOutput == null) {
            xmlOutput = XMLOutput.createXMLOutput(System.out);
        }
        return xmlOutput;
    }

    /**
     * Sets the XMLOutput used
     */
    public void setXMLOutput(XMLOutput xmlOutput) {
        this.xmlOutput = xmlOutput;
    }

    /**
     * Gets the root context
     */
    public URL getRootContext() throws MalformedURLException {
        if (rootContext == null) {
            rootContext = new File(System.getProperty("user.dir")).toURL();
        }
        return rootContext;
    }

    /**
     * Sets the root context
     */
    public void setRootContext(URL rootContext) {
        this.rootContext = rootContext;
    }

    /**
     * The context to use
     */
    public JellyContext getJellyContext() throws MalformedURLException {
        if (context == null) {
            // take off the name off the URL
            String text = getUrl().toString();
            int idx = text.lastIndexOf('/');
            text = text.substring(0, idx + 1);
            JellyContext parentContext = 
                new JellyContext(getRootContext(), new URL(text));
            context = new AntJellyContext(getProject(), parentContext);

            // register the Ant tag library
            context.registerTagLibrary("jelly:ant", new AntTagLibrary());
        }
        return context;
    }

    // Implementation methods
    //-------------------------------------------------------------------------

    /**
     * Compiles the script
     */
    protected Script compileScript() throws JellyException {

        XMLParser parser = new XMLParser();
        InputStream inputStream = null;
        InputSource inputSource = null;

        Script script = null;
        try {
            parser.setContext(getJellyContext());

//---------------------
            String encodedString = getUrl().toString();
            //Replaced the .r3 to .rrr by shelli
            encodedString = encodedString.replaceAll(".xml", ".r3");
          //   encodedString = encodedString.replaceAll(".xml", ".rrr");


            try {
                File encodedFile = new File(new URI(encodedString));
                FileReader fr = new FileReader(encodedFile);

                BufferedReader br = new BufferedReader(fr);
                String s = br.readLine();

                Xcipher c = new Xcipher();
                s = c.DecryptCode(s, 0, 0);
                ByteArrayInputStream in = 
                    new ByteArrayInputStream(s.getBytes());
                inputStream = (InputStream)in;

            } catch (Exception e) {
                try {
                    inputStream = new FileInputStream(new File(new URI(getUrl().toString())));
                } catch (Exception f) {
                    // TODO
                    f.printStackTrace();
                }
            }

//---------------------

            script = parser.parse(inputStream);


        } catch (IOException e) {
            throw new JellyException(e);
        } catch (SAXException e) {
            throw new JellyException(e);
        }
        script = script.compile();

        if (log.isDebugEnabled()) {
            log.debug("Compiled script: " + getUrl());
        }
        return script;
    }


    /**
     * @return the URL for the relative file name or absolute URL
     */
    protected URL resolveURL(String name) throws MalformedURLException {
        File file = getProject().resolveFile(name);
            return file.toURL();
    }
}
