
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    static ArrayList<ClassInfo> classList = new ArrayList<>();
    static ArrayList<String> files = new ArrayList<>();
    private final String REG_EXP_METHOD = "\\s(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\],\\s]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        loadFiles(new File("C:\\Users\\fsobrado\\Documents\\NetBeansProjects\\CodeClonesTestProject\\src\\main\\java"));
        Main obj = new Main();
        obj.getJavaClass(files);
    }

    public void code() {
        getJavaClass(files);
    }

    public static void loadFiles(File node) {
        System.out.println(node.getAbsoluteFile());
        Main obj = new Main();

        if (node.isDirectory()) {
            String[] subNode = node.list();
            for (String filename : subNode) {
                if (filename.contains(".java")) {
                    files.add(filename);
                    obj.copy(node.getName() + "/" + filename, filename);
                }
                loadFiles(new File(node, filename));
            }
        }

    }

    public void getJavaClass(ArrayList<String> files) {
        for (int i = 0; i < files.size(); i++) {
            String url = files.get(i);
            classList.add(getFileFromResources("project\\" + url));
        }
        System.out.println(classList);
    }

    private ClassInfo getFileFromResources(String fileName) {
        File file = null;
        ClassInfo classInfo = new ClassInfo();
        
        file = new File(fileName);
        if (file == null) {
            throw new IllegalArgumentException("file is not found!");
        }

        Scanner sc;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        classInfo.setName(fileName);
        
        boolean insideMethod = false;
        ArrayList<MethodInfo> methodList = new ArrayList<>();
        StringBuilder content = new StringBuilder();
        String methodName = "";
        classInfo.setMethodList(methodList);
        
        while (sc.hasNextLine()) {
            String nextLine = sc.nextLine();
            if (nextLine.matches(REG_EXP_METHOD)) {
                insideMethod = true;
                methodName = nextLine;
                MethodInfo methodInfo = new MethodInfo();
                methodInfo.setMethodName(methodName);
                methodList.add(methodInfo);
                content = new StringBuilder();
                continue;
            }

            if (insideMethod) {
                content.append(nextLine);
                for (MethodInfo method : methodList) {
                    if (method.getMethodName().equals(methodName)) {
                        method.setContent(content.toString());
                    }
                }
            }
        }

        return classInfo;
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
