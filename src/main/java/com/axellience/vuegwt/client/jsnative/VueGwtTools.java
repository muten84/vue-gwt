package com.axellience.vuegwt.client.jsnative;

import com.axellience.vuegwt.client.VueComponent;
import com.axellience.vuegwt.client.VueDirective;
import com.axellience.vuegwt.client.definitions.VueComponentDefinition;
import com.axellience.vuegwt.client.jsnative.types.JsObject;
import com.google.gwt.regexp.shared.RegExp;
import jsinterop.annotations.JsMethod;

/**
 * This object provides utils methods for VueGWT internal processing
 * @author Adrien Baron
 */
public class VueGwtTools
{
    private static RegExp camelCasePattern = RegExp.compile("([a-z])([A-Z]+)", "g");
    private static RegExp componentEnd     = RegExp.compile("Component$");
    private static RegExp directiveEnd     = RegExp.compile("Directive$");

    @JsMethod(namespace = "vueGwt")
    public static native <T extends VueComponent> T createVueInstance(
        VueComponentDefinition vueComponentDefinition);

    @JsMethod(namespace = "vueGwt")
    public static native <T extends VueComponent> T createInstanceForVueClass(
        JsObject extendedVueClass);

    /**
     * Return the default name to register a component based on it's class name
     * The name of the tag is the name of the component converted to kebab-case
     * If the component class ends with "Component", this part is ignored
     * @param vueComponentClass
     */
    public static String componentToTagName(Class<? extends VueComponent> vueComponentClass)
    {
        String name = vueComponentClass.getSimpleName();
        // Drop "Component" at the end of the class name
        name = componentEnd.replace(name, "");
        // Convert from CamelCase to kebab-case
        return camelCasePattern.replace(name, "$1-$2").toLowerCase();
    }

    /**
     * Return the default name to register a directive based on it's class name
     * The name of the tag is the name of the component converted to kebab-case
     * If the component class ends with "Directive", this part is ignored
     * @param vueDirective
     */
    public static String directiveToTagName(VueDirective vueDirective)
    {
        String name = vueDirective.getClass().getSimpleName();
        // Drop "Component" at the end of the class name
        name = directiveEnd.replace(name, "");
        // Convert from CamelCase to kebab-case
        return camelCasePattern.replace(name, "$1-$2").toLowerCase();
    }
}
