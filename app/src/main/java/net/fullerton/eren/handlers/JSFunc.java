package net.fullerton.eren.handlers;

import android.webkit.ValueCallback;
import android.webkit.WebView;

public class JSFunc {

    public static void setTextField(WebView mWebView, String elementId, String value){
        mWebView.loadUrl("javascript: (function() {document.getElementById('" + elementId + "').value = '" + value + "'}) ();");
    }

    public static void clickButton(WebView mWebView, String className, int index){
        mWebView.loadUrl("javascript: (function() {document.getElementsByClassName('" + className + "')[" + String.valueOf(index) + "].click()}) ();");
    }

    public static void clickButton(WebView mWebView, String elementId, String typ){
        switch(typ){
            case "click":
                mWebView.loadUrl("javascript: (function() {document.getElementById('" + elementId + "').click()}) ();");
                break;
            case "submit":
                mWebView.loadUrl("javascript: (function() {document.getElementById('" + elementId + "').submit()}) ();");
                break;
        }
    }

    public static void alert(WebView mWebView, String message){
        mWebView.loadUrl("javascript: (function() {setTimeout(function() { alert(" + message + ")}, 500)}) ();");
    }

    //MODAL TOP FUNC
    public static void modalTopValue(WebView mWebView, String elementId, String value){
        mWebView.loadUrl("javascript: (function() {window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').value = '" + value + "'}) ();");
    }

    public static void modalTopClick(WebView mWebView, String elementId, String typ){
        switch(typ){
            case "click":
                mWebView.loadUrl("javascript: (function() {window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').click()}) ();");
                break;
            case "submit":
                mWebView.loadUrl("javascript: (function() {window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').submit()}) ();");
                break;
        }
    }

    public static void modalTopChildren(WebView mWebView, String elementId){
        mWebView.loadUrl("javascript: (function() {window.frames[\"ModalTop\"].document.getElementById('" + elementId + "').children[0].children[0].innerText}) ();");
    }

    public static void grabSchedule(WebView mWebView) {

        mWebView.evaluateJavascript("(function() { return window.frames[\"ModalTop\"].document.getElementById('CLASS_NAME$span$0').innerText; }) ()", new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                alert(mWebView, s);
            }
        });
    }
}
