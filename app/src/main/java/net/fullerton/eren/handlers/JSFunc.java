package net.fullerton.eren.handlers;

import android.webkit.ValueCallback;
import android.webkit.WebView;

public class JSFunc {

    public static void setTextField(WebView mWebView, String elementId, String value){
        mWebView.evaluateJavascript("document.getElementById('" + elementId + "').value = '" + value +"'", null);
    }

    public static void clickButton(WebView mWebView, String className, int index){
        mWebView.evaluateJavascript("document.getElementsByClassName('" + className + "')[0].click()", null);
    }

    public static void clickButton(WebView mWebView, String elementId, String typ){
        switch(typ){
            case "click":
                mWebView.evaluateJavascript("document.getElementById('" + elementId + "').click()", null);
                break;
            case "submit":
                mWebView.evaluateJavascript("document.getElementById('" + elementId + "').submit()", null);
                break;
        }
    }

    public static void alert(WebView mWebView, String message, ValueCallback<String> valueCallback){
        mWebView.evaluateJavascript("alert(" + message + ")", valueCallback);
    }

    public static void returnValues(WebView mWebView, String message, ValueCallback<String> valueCallback){
        mWebView.evaluateJavascript(message, valueCallback);
    }

    //MODAL TOP FUNC
    public static void modalTopValue(WebView mWebView, String elementId, String value){
        mWebView.evaluateJavascript("window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').value = '" + value + "'", null);
    }

    public static void modalTopClick(WebView mWebView, String elementId, String typ){
        switch(typ){
            case "click":
                mWebView.evaluateJavascript("window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').click()", null);
                break;
            case "submit":
                mWebView.evaluateJavascript("window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').submit()", null);
                break;
        }
    }

    public static void modalTopChildren(WebView mWebView, String elementId){
        mWebView.loadUrl("javascript: (function() {window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').children[0].children[0].innerText}) ();");
    }

    //asks the user for number of classes to iterate and retrieve the class
    public static void grabSchedule(WebView mWebView, int numOfClasses) {
        String myString = Integer.toString(numOfClasses);
        mWebView.evaluateJavascript("(function() { return window.frames[\"ModalTop\"].document.getElementById('CLASS_NAME$span$"+ myString + "').innerText; }) ()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                   // alert(mWebView, s);
            }
        });
    }

}
