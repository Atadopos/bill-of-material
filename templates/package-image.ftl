<#if osName?upper_case?contains("WIN")>
    --java-options '--module-path $APPDIR/../runtime/lib/javafx/ --add-modules=javafx.controls,javafx.fxml,javafx.base'
<#elseif osName?upper_case?contains("MAC")>
    --java-options '--module-path $APPDIR/../runtime/lib/javafx/ --add-modules=javafx.controls,javafx.fxml,javafx.base'
<#else>
    --java-options '--module-path $APPDIR/../runtime/lib/javafx/ --add-modules=javafx.controls,javafx.fxml,javafx.base'
</#if>
