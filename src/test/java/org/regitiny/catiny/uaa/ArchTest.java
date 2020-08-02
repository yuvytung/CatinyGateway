package org.regitiny.catiny.uaa;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("org.regitiny.catiny.uaa");

        noClasses()
            .that()
                .resideInAnyPackage("org.regitiny.catiny.uaa.service..")
            .or()
                .resideInAnyPackage("org.regitiny.catiny.uaa.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..org.regitiny.catiny.uaa.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
