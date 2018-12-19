<?xml version="1.0"?>
<#import "root://activities/common/kotlin_macros.ftl" as kt>
<recipe>
    <!-- <@kt.addAllKotlinDependencies /> -->
    <!-- <#if useSupport><dependency mavenUrl="com.android.support:support-v4:${buildApi}.+"/></#if> -->
    <!-- <merge from="root/res/values/strings.xml" to="${escapeXmlAttribute(resOut)}/values/strings.xml" /> -->

    <#if includeLayout>
        <instantiate from="root/res/layout/fragment_xky_mvp.xml.ftl"
                       to="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml" />

        <open file="${escapeXmlAttribute(resOut)}/layout/${escapeXmlAttribute(fragmentName)}.xml" />
    </#if>

    <instantiate from="root/src/app_package/XkyMvpFragment.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${className}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/XkyMvpContract.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${contractName}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/XkyMvpPresenter.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${presenterName}.${ktOrJavaExt}" />

    <instantiate from="root/src/app_package/XkyMvpModel.${ktOrJavaExt}.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${modelName}.${ktOrJavaExt}" />

    <open file="${escapeXmlAttribute(srcOut)}/${className}.${ktOrJavaExt}" />
</recipe>
