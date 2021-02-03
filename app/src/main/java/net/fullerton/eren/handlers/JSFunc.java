package net.fullerton.eren.handlers;

import android.webkit.WebView;

public class JSFunc {

    public static void setTextField(WebView mWebView, String elementId, String value){
        mWebView.loadUrl("javascript: (function() {document.getElementById('" + elementId + "').value = '" + value + "'}) ();");
    }

    public static void clickButton(WebView mWebView, String className, int index){
        mWebView.loadUrl("javascript: (function() {document.getElementsByClassName('" + className + "')[" + String.valueOf(index) + "].click()}) ();");
    }

    public static void alert(WebView mWebView, String message){
        mWebView.loadUrl("javascript: (function() {alert(" + message + ")}) ();");
    }
}
