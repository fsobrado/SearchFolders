
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author JMendez
 */
public class Main {

    static ArrayList<String> files = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        displayIt(new File("C:\\Users\\fsobrado\\Documents\\NetBeansProjects\\CodeClonesTestProject\\src\\main\\java"));
        System.out.println(files);
        Main obj = new Main();
        obj.getJavaClass(files);
    }

    public void code() {
        getJavaClass(files);
    }

    public static void displayIt(File node) {
        System.out.println(node.getAbsoluteFile());
        Main obj = new Main();

        if (node.isDirectory()) {
            String[] subNode = node.list();
            for (String filename : subNode) {
                if (filename.contains(".java")) {
                    files.add(filename);
                    obj.copy(node.getName() + "/" + filename, filename);
                }
                displayIt(new File(node, filename));
            }
        }

    }

    public void getJavaClass(ArrayList<String> files) {
        ArrayList<Class> javaClass = new ArrayList();

        for (int i = 0; i < files.size(); i++) {
            String url = files.get(i);
            javaClass.add(getFileFromResources(url).getClass());
        }
        System.out.println(javaClass);
    }

    private File getFileFromResources(String fileName) {
        ClassLoader classLoader = Main.class.getClassLoader();

        try {
            Class aClass = classLoader.loadClass(fileName);
            System.out.println("aClass.getName() = " + aClass.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        File file = new File(fileName);

        if (file == null) {
            throw new IllegalArgumentException("file is not found!");
        } else {
            return (file);
        }
    }

    public void copy(String fileNameA, String fileNameB) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            File afile = new File("C:\\Users\\fsobrado\\Documents\\NetBeansProjects\\CodeClonesTestProject\\src\\main\\java\\" + fileNameA);
            File bfile = new File("C:\\Users\\fsobrado\\Documents\\NetBeansProjects\\SearchFolder\\project\\" + fileNameB);

            inStream = new FileInputStream(afile);
            outStream = new FileOutputStream(bfile);

            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }

            inStream.close();
            outStream.close();

            //delete the original file
            //afile.delete();
            System.out.println("File is copied successful!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
