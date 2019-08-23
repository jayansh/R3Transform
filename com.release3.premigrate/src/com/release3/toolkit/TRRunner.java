package com.release3.toolkit;

import java.io.File;

import java.io.FileOutputStream;
import java.io.PrintStream;

import java.util.Vector;
import java.util.logging.Logger;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.BuildListener;
import org.apache.tools.ant.BuildLogger;
import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.apache.tools.ant.input.DefaultInputHandler;
import org.apache.tools.ant.input.InputHandler;
import org.apache.tools.ant.util.ClasspathUtils;

public class TRRunner {
    private Project project;

    /** Stream to use for logging. */
    private static PrintStream out = System.out;

    /** Stream that we are using for logging error messages. */
    private static PrintStream err = System.out;

    /** Names of classes to add as listeners to project. */
    private Vector listeners = new Vector(1);

    /**
     * The Ant InputHandler class.  There may be only one input
     * handler.
     */
    private String inputHandlerClassname = null;
    private String propertyFileDir = null;
    private String buildXmlFileDir = null;
    private String buildFileName=null;

    /**
     * Whether or not output to the log is to be unadorned.
     */
    private boolean emacsMode = false;

    /**
     * The Ant logger class. There may be only one logger. It will have
     * the right to use the 'out' PrintStream. The class must implements the
     * BuildLogger interface.
     */
    private String loggerClassname = null;

    /** Our current message output status. Follows Project.MSG_XXX. */
    private int msgOutputLevel = Project.MSG_INFO;


    public void init(String _buildXMLDir, String _baseDir) throws Exception {
        // Create a new project, and perform some default initialization
        project = new Project();
        project.setProperty("property.file.directory", 
                            this.getPropertyFileDir());
            
        project.setProperty("via.directory", _buildXMLDir);
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        System.out.println("the via directory is : " + _buildXMLDir);
        System.out.println("********************************************");

        try {
            project.init();
        } catch (BuildException e) {
            throw new Exception("The default task list could not be loaded.");
        }
        addBuildListeners(project);
        addInputHandler(project);

        // Set the base directory. If none is given, "." is used.
        if (_baseDir == null)
            _baseDir = new String(".");
        try {
            project.setBasedir(_baseDir);
        } catch (BuildException e) {
            throw new Exception("The given basedir doesn't exist, or isn't a directory.");
        }

        // Parse the given buildfile. If none is given, "build.xml" is used.
         String _buildFile=null;
         if ( buildFileName == null)
             _buildFile = _buildXMLDir+"/build.xml";
         else
             _buildFile = _buildXMLDir+"/"+buildFileName;
         
        try {
            ProjectHelper.getProjectHelper().parse(project, 
                                                   new File(_buildFile));
        } catch (BuildException e) {
            throw new Exception("Configuration file " + _buildFile + 
                                " is invalid, or cannot be read.");
        }
    }

    public void runTarget(String _target) throws Exception {
        // Test if the project exists
        if (project == null)
            throw new Exception("No target can be launched because the project has not been initialized. Please call the 'init' method first !");

        // If no target is specified, run the default one.
        if (_target == null)
            _target = project.getDefaultTarget();


        // Run the target
        try {
            project.executeTarget(_target);
        } catch (BuildException e) {
           e.printStackTrace(this.err);
        }
    }

    protected void addBuildListeners(Project project) {

        // Add the default listener
        project.addBuildListener(createLogger());

        for (int i = 0; i < listeners.size(); i++) {
            String className = (String)listeners.elementAt(i);
            BuildListener listener = 
                (BuildListener)ClasspathUtils.newInstance(className, 
                                                          this.getClass().getClassLoader(), 
                                                          BuildListener.class);
            project.setProjectReference(listener);

            project.addBuildListener(listener);
        }
    }


    private void addInputHandler(Project project) throws BuildException {
        InputHandler handler = null;
        if (inputHandlerClassname == null) {
            handler = new DefaultInputHandler();
        } else {
            handler = 
                    (InputHandler)ClasspathUtils.newInstance(inputHandlerClassname, 
                                                             this.getClass().getClassLoader(), 
                                                             InputHandler.class);
            project.setProjectReference(handler);
        }
        project.setInputHandler(handler);
    }

    private BuildLogger createLogger() {
        BuildLogger logger = null;
        if (loggerClassname != null) {
            try {
                logger = 
                        (BuildLogger)ClasspathUtils.newInstance(loggerClassname, 
                                                                this.getClass().getClassLoader(), 
                                                                BuildLogger.class);
     
                                                                
            } catch (BuildException e) {
                System.err.println("The specified logger class " + 
                                   loggerClassname + 
                                   " could not be used because " + 
                                   e.getMessage());
                throw new RuntimeException();
            }
        } else {
            logger = new DefaultLogger();
        }

        logger.setMessageOutputLevel(msgOutputLevel);
        logger.setOutputPrintStream(out);
        logger.setErrorPrintStream(err);
        logger.setEmacsMode(emacsMode);
        return logger;
    }

    public static void main(String[] args) {

        TRRunner ant = new TRRunner();

        if (args.length < 1) {
            System.out.println("You have to specify target file");
        }
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-debug"))
                ant.setMsgOutputLevel(Project.MSG_DEBUG);
            if (args[i].equals("-p")) {
                if (i < args.length - 1) {
                    ant.setPropertyFileDir(args[++i]);
                } else
                    ant.setPropertyFileDir(".");
            }

            if (args[i].equals("-b")) {
                if (i < args.length - 1) {
                    ant.setBuildXmlFileDir(args[++i]);
                } else
                    ant.setBuildXmlFileDir(".");
            }

            if (args[i].equals("-bfile")) {
                if (i < args.length - 1) {
                    ant.setBuildFileName(args[++i]);
                }
            }
            


            if (args[i].equals("-logfile")) {
                if (i < args.length - 1) {
                    String logfile = args[++i];
                    File logFile = new File(logfile);
                    try {
                        PrintStream logTo = 
                            new PrintStream(new FileOutputStream(logFile));
                        ant.setOut(logTo);
                        ant.setErr(logTo);
                    } catch (Exception e) {
                        String msg = 
                            "Cannot write on the specified log file. " + 
                            "Make sure the path exists and you have write " + 
                            "permissions.";
                        System.out.println(msg);
                        System.exit(-1);
                    }
                }
            }

        }

        if (ant.getPropertyFileDir() == null) {
            ant.setPropertyFileDir(new File(".").getAbsolutePath());
        }
        if (ant.getBuildXmlFileDir() == null) {
            ant.setBuildXmlFileDir(new File(".").getAbsolutePath());
        }

        try {
            ant.init(ant.getBuildXmlFileDir() , 
                     ant.getBuildXmlFileDir()
                     );
            ant.runTarget("gen");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setPropertyFileDir(String propertyFileDir) {
        this.propertyFileDir = propertyFileDir;
    }

    public String getPropertyFileDir() {
        return propertyFileDir;
    }

    public void setMsgOutputLevel(int msgOutputLevel) {
        this.msgOutputLevel = msgOutputLevel;
    }

    public void setBuildXmlFileDir(String buildXmlFileDir) {
        this.buildXmlFileDir = buildXmlFileDir;
    }

    public String getBuildXmlFileDir() {
        return buildXmlFileDir;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public void setErr(PrintStream err) {
        this.err = err;
    }

    public void setBuildFileName(String buildFileName) {
        this.buildFileName = buildFileName;
    }

    public String getBuildFileName() {
        return buildFileName;
    }
}

