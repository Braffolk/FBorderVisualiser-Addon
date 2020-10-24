package ee.braffolk.factionsx

import net.prosavage.factionsx.command.engine.CommandInfo
import net.prosavage.factionsx.command.engine.CommandRequirementsBuilder
import net.prosavage.factionsx.command.engine.FCommand


class PerformanceArgument : FCommand.ArgumentType() {
  override fun getPossibleValues(fPlayer: net.prosavage.factionsx.core.FPlayer?): List<String> = listOf("fast", "normal", "fancy")
}

class CmdVisualise(private val visualisationHandler: VisualisationHandler) : FCommand() {

  init {
    aliases.add("visualise-borders")
    aliases.add("v-b")
    optionalArgs.add(Argument("speed", 0, PerformanceArgument()))

    commandRequirements = CommandRequirementsBuilder()
        .asPlayer(true)
        .build()
    visualisationHandler.shapeCache.createAllMeshes()
  }

  override fun execute(info: CommandInfo): Boolean {
    val player = info.player

    val performance = if(info.args.size >= 1) {
      VisualisationPerformance.valueOf(info.args[0])
    } else {
      VisualisationPerformance.Normal
    }

    if(!visualisationHandler.hasPlayer(player!!)) {
      visualisationHandler.addPlayer(player, performance)
      info.message("Turned borders on")
    } else {
      visualisationHandler.removePlayer(player)
      info.message("Turned borders off")
    }
    return true
  }

  /**
   * This is used by the command engine to tell a player what a command does in the help menu
   */
  override fun getHelpInfo(): String {
    return "visualises faction borders near the player"
  }
}