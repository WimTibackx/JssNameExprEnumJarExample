package be.wimtibackx.experiments.jss_nameexpr_enum_jar_example.source_under_test;

import be.wimtibackx.experiments.jss_nameexpr_enum_jar_example.enum_definer.ExampleAnnotation;

import static be.wimtibackx.experiments.jss_nameexpr_enum_jar_example.enum_definer.ExampleEnum.FOO;

@ExampleAnnotation(exampleEnum = FOO)
public class SourceClass {
}
