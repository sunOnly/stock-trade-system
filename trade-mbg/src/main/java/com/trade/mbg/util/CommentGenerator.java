package com.trade.mbg.util;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.DefaultCommentGenerator;
import org.mybatis.generator.internal.util.StringUtility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Set;

/**
 * Custom Comment Generator for MyBatis Generator.
 * It adds @ApiModelProperty annotations for Swagger and uses Lombok annotations.
 */
public class CommentGenerator extends DefaultCommentGenerator {

    private boolean suppressDate;
    private boolean suppressAllComments;
    private boolean addRemarkComments;
    private SimpleDateFormat dateFormat;
    private boolean useLombok;
    private boolean useSwagger;

    public CommentGenerator() {
        super();
        suppressDate = false;
        suppressAllComments = false;
        addRemarkComments = false;
        useLombok = true; // Enable Lombok by default
        useSwagger = true; // Enable Swagger by default
    }

    @Override
    public void addJavaFileComment(CompilationUnit compilationUnit) {
        // add no file level comments
        if (suppressAllComments) {
            return;
        }
        if (useLombok) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("lombok.Builder"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("lombok.NoArgsConstructor"));
            compilationUnit.addImportedType(new FullyQualifiedJavaType("lombok.AllArgsConstructor"));
        }
        if (useSwagger) {
            compilationUnit.addImportedType(new FullyQualifiedJavaType("io.swagger.v3.oas.annotations.media.Schema"));
        }
    }

    @Override
    public void addModelClassComment(TopLevelClass topLevelClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments || !addRemarkComments) {
            return;
        }

        topLevelClass.addJavaDocLine("/**");
        String remarks = introspectedTable.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            topLevelClass.addJavaDocLine(" * " + remarks);
            topLevelClass.addJavaDocLine(" *");
        }
        topLevelClass.addJavaDocLine(" * " + introspectedTable.getFullyQualifiedTable());

        if (!suppressDate) {
            topLevelClass.addJavaDocLine(" * @date " + getDateString());
        }
        topLevelClass.addJavaDocLine(" */");

        if (useLombok) {
            topLevelClass.addAnnotation("@Data");
            topLevelClass.addAnnotation("@Builder");
            topLevelClass.addAnnotation("@NoArgsConstructor");
            topLevelClass.addAnnotation("@AllArgsConstructor");
        }
        if (useSwagger) {
            if (StringUtility.stringHasValue(remarks)) {
                topLevelClass.addAnnotation("@Schema(description = \"" + remarks + "\")");
            } else {
                topLevelClass.addAnnotation("@Schema(description = \"" + introspectedTable.getFullyQualifiedTable().getDomainObjectName() + "\")");
            }
        }
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        if (suppressAllComments) {
            return;
        }

        String remarks = introspectedColumn.getRemarks();
        if (addRemarkComments && StringUtility.stringHasValue(remarks)) {
            field.addJavaDocLine("/**");
            field.addJavaDocLine(" * " + remarks);
            field.addJavaDocLine(" */");
            if (useSwagger) {
                field.addAnnotation("@Schema(description = \"" + remarks + "\")");
            }
        } else if (useSwagger) {
             field.addAnnotation("@Schema(description = \"" + introspectedColumn.getActualColumnName() + "\")");
        }
    }

    @Override
    public void addGetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // Lombok will generate getters, so no need to add comments here if Lombok is enabled.
        if (useLombok || suppressAllComments) {
            return;
        }
        super.addGetterComment(method, introspectedTable, introspectedColumn);
    }

    @Override
    public void addSetterComment(Method method, IntrospectedTable introspectedTable, IntrospectedColumn introspectedColumn) {
        // Lombok will generate setters, so no need to add comments here if Lombok is enabled.
        if (useLombok || suppressAllComments) {
            return;
        }
        super.addSetterComment(method, introspectedTable, introspectedColumn);
    }

    @Override
    public void addConfigurationProperties(Properties properties) {
        super.addConfigurationProperties(properties);
        suppressDate = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));
        suppressAllComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
        addRemarkComments = StringUtility.isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_ADD_REMARK_COMMENTS));
        useLombok = StringUtility.isTrue(properties.getProperty("useLombok", "true")); // Default to true
        useSwagger = StringUtility.isTrue(properties.getProperty("useSwagger", "true")); // Default to true

        String dateFormatString = properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_DATE_FORMAT);
        if (StringUtility.stringHasValue(dateFormatString)) {
            dateFormat = new SimpleDateFormat(dateFormatString);
        }
    }

    @Override
    protected String getDateString() {
        if (suppressDate) {
            return null;
        }
        if (dateFormat != null) {
            return dateFormat.format(new Date());
        }
        return new Date().toString();
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        super.addClassComment(innerClass, introspectedTable);
    }

    @Override
    public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        super.addEnumComment(innerEnum, introspectedTable);
    }

    @Override
    public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        super.addFieldComment(field, introspectedTable);
    }

    @Override
    public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
        if (suppressAllComments) {
            return;
        }
        super.addGeneralMethodComment(method, introspectedTable);
    }

    @Override
    public void addRootComment(XmlElement rootElement) {
        // add no xml comments
    }

    @Override
    public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
        if (suppressAllComments) {
            return;
        }
        super.addClassComment(innerClass, introspectedTable, markAsDoNotDelete);
    }
}