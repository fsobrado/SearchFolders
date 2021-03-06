
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
 * @author Fede
 */
public class CustomProjectClassParser {

    private static ArrayList<String> classList = new ArrayList<>();
    private static final String PROJECT_DEFAULT_FOLDER = ".\\project\\";
    private static final List<String> IGNORE_FOLDERS_LIST = Arrays.asList(".git", "target");

    public static List<ClassInfo> loadClasses(String projectUrl) {
        //Check default file locations
        File projectFile = new File(projectUrl);
        if (!projectFile.exists()) {
            try {
                projectFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CustomProjectClassParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        new File(PROJECT_DEFAULT_FOLDER).mkdirs();
        //
        loadFiles(projectUrl, projectFile);

        if (classList.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        List<ClassInfo> classInfoList = new ArrayList<>();
        for (String fileName : classList) {
            ClassInfo classInfo = new ClassInfo();
            File file = new File(PROJECT_DEFAULT_FOLDER + fileName);

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
            int openKey = 0;
            int closeKey = 0;

            while (sc.hasNextLine()) {
                String nextLine = sc.nextLine();
                //Ignore comments lines
                if (nextLine.trim().matches(RegExpEnum.COMMENTS.getType())) {
                    continue;
                }
                if (openKey == closeKey) {
                    insideMethod = false;
                }

                if (nextLine.trim().matches(RegExpEnum.REG_EXP_METHOD.getType())) {
                    openKey++;
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
                    if (content.toString().contains(StructuresInfoEnum.OPEN_STRUC.getType())) {
                        openKey++;
                    }
                    if (content.toString().contains(StructuresInfoEnum.CLOSE_STRUC.getType())) {
                        closeKey++;
                    }

                    for (MethodInfo method : methodList) {
                        if (method.getMethodName().equals(methodName)) {
                            method.setContent(content.toString());
                        }
                    }
                }
            }
            classInfoList.add(classInfo);
        }

        return classInfoList;
    }

    private static void loadFiles(String projectUrl, File node) {
        if (node.isDirectory() && !IGNORE_FOLDERS_LIST.contains(node.getName())) {
            String[] subNode = node.list();
            for (String filename : subNode) {
                if (filename.contains(".java")) {
                    classList.add(filename);
                    copy(node.getPath(), filename);
                }
                loadFiles(projectUrl, new File(node, filename));
            }
        }

    }

    private static void copy(String projectUrl, String fileName) {
        InputStream inStream = null;
        OutputStream outStream = null;

        try {

            File fromFile = new File(projectUrl, fileName);
            File toFile = new File(PROJECT_DEFAULT_FOLDER + fileName);

            inStream = new FileInputStream(fromFile);
            outStream = new FileOutputStream(toFile);

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
            System.out.println("File copied successfully!");

        } catch (IOException e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
