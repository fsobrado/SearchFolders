
import java.util.List;

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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<ClassInfo> loadClasses = CustomProjectClassParser.loadClasses("C:\\Users\\Fede\\Documents\\NetBeansProjects\\Clones_TestProject\\");

        for (ClassInfo classInfo : loadClasses) {
            System.out.println(classInfo.getName());
            for (MethodInfo method : classInfo.getMethodList()) {
                System.out.println(method.getMethodName());
                System.out.println(method.getContent());
            }
        }
    }

}
