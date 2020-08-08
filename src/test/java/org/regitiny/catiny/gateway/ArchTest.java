package org.regitiny.catiny.gateway;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest
{

  @Test
  void servicesAndRepositoriesShouldNotDependOnWebLayer()
  {

    JavaClasses importedClasses = new ClassFileImporter()
      .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
      .importPackages("org.regitiny.catiny.gateway");

    noClasses()
      .that()
      .resideInAnyPackage("org.regitiny.catiny.gateway.service..")
      .or()
      .resideInAnyPackage("org.regitiny.catiny.gateway.repository..")
      .should().dependOnClassesThat()
      .resideInAnyPackage("..org.regitiny.catiny.gateway.web..")
      .because("Services and repositories should not depend on web layer")
      .check(importedClasses);
  }
}
