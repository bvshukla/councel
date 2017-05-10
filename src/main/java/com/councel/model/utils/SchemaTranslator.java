package com.councel.model.utils;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.logging.Logger;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.hbm2ddl.SchemaExport.Action;
import org.hibernate.tool.schema.TargetType;

public class SchemaTranslator {

    public static void main(String[] args) throws Exception {

        File f = new File(".");
        String directory = f.getAbsoluteFile() + "/src/main/resources/ddl/generated/";

        String packageName[] = { "com.councel.model.pojo" };

        SchemaTranslator gen = new SchemaTranslator();
      
        gen.generate(Dialect.MYSQL, directory,packageName);

    }


    @SuppressWarnings("rawtypes")
    public List<Class> getClasses(String packageName) throws Exception {
        File directory = null;
        try {
            ClassLoader cld = getClassLoader();
            URL resource = getResource(packageName, cld);
            directory = new File(resource.getFile());
        } catch (NullPointerException ex) {
            throw new ClassNotFoundException(packageName + " (" + directory + ") does not appear to be a valid package");
        }
        return collectClasses(packageName, directory);
    }

    private ClassLoader getClassLoader() throws ClassNotFoundException {
        ClassLoader cld = Thread.currentThread().getContextClassLoader();
        if (cld == null) {
            throw new ClassNotFoundException("Can't get class loader.");
        }
        return cld;
    }

    private URL getResource(String packageName, ClassLoader cld) throws ClassNotFoundException {
        String path = packageName.replace('.', '/');
        URL resource = cld.getResource(path);
        if (resource == null) {
            throw new ClassNotFoundException("No resource for " + path);
        }
        return resource;
    }

    @SuppressWarnings("rawtypes")
    private List<Class> collectClasses(String packageName, File directory) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (directory.exists()) {
            String[] files = directory.list();
            for (String file : files) {
                if (file.endsWith(".class")) {
                    // removes the .class extension
                    classes.add(Class.forName(packageName + '.' + file.substring(0, file.length() - 6)));
                }
            }
        } else {
            throw new ClassNotFoundException(packageName + " is not a valid package");
        }
        return classes;
    }

    private void generate(Dialect dialect, String directory, String[] packagesName) throws Exception {

        MetadataSources metadata = new MetadataSources(
                new StandardServiceRegistryBuilder()
                        .applySetting("hibernate.dialect", dialect.getDialectClass())
                        .build());

        for (String packageName : packagesName) {
            
            for (Class clazz : getClasses(packageName)) {
              
                metadata.addAnnotatedClass(clazz);
            }
        }

        SchemaExport export = new SchemaExport();
        
        EnumSet<TargetType> targets = EnumSet.of(TargetType.STDOUT);
        
        export.setDelimiter(";");
        export.setOutputFile(directory + "ddl_" + dialect.name().toLowerCase() + ".sql");
        export.setFormat(true);
        export.execute(targets, Action.CREATE, metadata.buildMetadata());
        
    }
    
    private static enum Dialect {
        ORACLE("org.hibernate.dialect.Oracle10gDialect"), MYSQL("org.hibernate.dialect.MySQLDialect"), HSQL(
                "org.hibernate.dialect.HSQLDialect"), H2("org.hibernate.dialect.H2Dialect");

        private String dialectClass;

        private Dialect(String dialectClass) {
            this.dialectClass = dialectClass;
        }

        public String getDialectClass() {
            return dialectClass;
        }
    }
}
