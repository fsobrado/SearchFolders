/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fede
 */
public enum RegExpEnum {
    /**
     * METHOD DETECTION
     */
    REG_EXP_METHOD("\\s(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\],\\s]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])");

    private String m_type;

    RegExpEnum(String p_type) {
        setType(p_type);
    }

    public String getType() {
        return m_type;
    }

    private void setType(String p_type) {
        this.m_type = p_type;
    }

}
