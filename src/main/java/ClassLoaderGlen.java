import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import org.apache.commons.io.FilenameUtils;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Glen
 */
public class ClassLoaderGlen extends ClassLoader {
    public ArrayList<Class> load() throws Exception {
        ArrayList<Class> classes = new ArrayList<>();
        File file = new File("C:\\Users\\Glen\\Documents\\NetBeansProjects\\SearchFolders\\project");
        //URLClassLoader classLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        File[] listOfFiles = file.listFiles();
        for (File fileName : listOfFiles) {
            String ext = FilenameUtils.getExtension(fileName.getName());
            if (ext.equals("class")) {
                // create FileInputStream object
                InputStream fileInputStream = new URLClassLoader(new URL[]{file.toURI().toURL()}).getResourceAsStream(fileName.getName());
                byte rawBytes[] = new byte[fileInputStream.available()];
                fileInputStream.read(rawBytes);
                Class<?> regeneratedClass = this.defineClass(rawBytes, 0, rawBytes.length);
                classes.add(regeneratedClass);
            }
            System.out.println(fileName);
        }
        return classes;
    }
}
