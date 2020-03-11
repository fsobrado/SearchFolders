
import java.util.List;
/**
 *
 * @author fsobrado
 */
public class ClassInfo {

    private String name;
    private String classPackage;
    private List<MethodInfo> methodList;

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the classPackage
     */
    public String getClassPackage() {
        return classPackage;
    }

    /**
     * @param classPackage the classPackage to set
     */
    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }

    /**
     * @return the methodList
     */
    public List<MethodInfo> getMethodList() {
        return methodList;
    }

    /**
     * @param methodList the methodList to set
     */
    public void setMethodList(List<MethodInfo> methodList) {
        this.methodList = methodList;
    }

}
