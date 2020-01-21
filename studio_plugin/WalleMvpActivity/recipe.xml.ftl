<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <#include "../common/recipe_manifest.xml.ftl" />
    <@kt.addAllKotlinDependencies />

    <!-- <dependency mavenUrl="com.android.support:appcompat-v7:${buildApi}.+"/>
    <dependency mavenUrl="com.android.support.constraint:constraint-layout:+" /> -->
    <!-- <dependency mavenUrl="android.arch.lifecycle:extensions:1.+"/>
    <#if generateKotlin && useAndroidX>
        <dependency mavenUrl="androidx.lifecycle:lifecycle-viewmodel-ktx:1.+"/>
    </#if> -->

    <instantiate from="root/res/layout/activity.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(activityLayout)}.xml" />
    <instantiate from="root/res/layout/fragment.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentLayout)}.xml" />
    <open file="${escapeXmlAttribute(resOut)}/layout/${fragmentLayout}.xml" />

    <instantiate from="root/src/app_package/Activity.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${activityClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/Fragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${fragmentClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${fragmentClass}.${ktOrJavaExt}" />

    <!-- <instantiate from="root/src/app_package/ViewModel.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${viewModelClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${viewModelClass}.${ktOrJavaExt}" /> -->

    <instantiate from="root/src/app_package/AcceptPar.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${acceptParClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${acceptParClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/Contract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${contractClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${contractClass}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/Presenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${presenterClass}.${ktOrJavaExt}" />
    <open file="${escapeXmlAttribute(srcOut)}/${fragmentPackage?replace('.', '/')}/${presenterClass}.${ktOrJavaExt}" />
</recipe>
