${pojo.getPackageDeclaration()}

<#assign classbody>
<#include "PojoTypeDeclaration.ftl"/> {

<#include "PojoFields.ftl"/>

<#include "PojoConstructors.ftl"/>
   
<#include "PojoPropertyAccessors.ftl"/>

	@Override
	public String toString() {
		return ${pojo.importType("org.apache.commons.lang.builder.ToStringBuilder")}.reflectionToString(this);
	}
}
</#assign>

${pojo.generateImports()}
${classbody}

