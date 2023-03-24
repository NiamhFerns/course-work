# How to create a custom entity.

- Create an implementation of the abstract class CustomMonster.
  - remember to attach associated level xml file by returning the Path of associated file in the getAssociatedLevelPath() method.
  - compile your class amongst the other required source files
  - create the file `META-INF/services/nz.ac.vuw.ecs.swen225.gp22.persistency2.monsterplugin.CustomMonster`
  - add line which is fully qualified classname of your implementation
  - run `jar cf level2.jar CustomMonsterProvider.class META-INF `
  - add the jar to the projects dependencies