/*
 * Copyright 2020 Marco Bignami.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.unknowndomain.alea.systems.projecthope2;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import net.unknowndomain.alea.command.HelpWrapper;
import net.unknowndomain.alea.systems.RpgSystemCommand;
import net.unknowndomain.alea.systems.RpgSystemDescriptor;
import net.unknowndomain.alea.roll.GenericRoll;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.javacord.api.entity.message.MessageBuilder;

/**
 *
 * @author journeyman
 */
public class ProjectHope2Command extends RpgSystemCommand
{
    private static final RpgSystemDescriptor DESC = new RpgSystemDescriptor("Project H.O.P.E. 2nd Edition", "ph2", "project-hope-2nd");
    
    private static final String THRESHOLD_PARAM = "threshold";
    private static final String POTENTIAL_PARAM = "potential";
    private static final String NOTATION_PARAM = "notation";
    
    private static final Options CMD_OPTIONS;
    
    static {
        CMD_OPTIONS = new Options();
        OptionGroup rollMode = new OptionGroup();
        CMD_OPTIONS.addOption(
                Option.builder("t")
                        .longOpt(THRESHOLD_PARAM)
                        .desc("Threshold for the dice pool between 10 and 3")
                        .hasArg()
                        .argName("thresholdValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("p")
                        .longOpt(POTENTIAL_PARAM)
                        .desc("Potential of the dice pool")
                        .hasArg()
                        .argName("potentialValue")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("n")
                        .longOpt(NOTATION_PARAM)
                        .desc("Roll in P/T notation")
                        .hasArg()
                        .argName("diceNotation")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("h")
                        .longOpt( CMD_HELP )
                        .desc( "Print help")
                        .build()
        );
        CMD_OPTIONS.addOption(
                Option.builder("v")
                        .longOpt(CMD_VERBOSE)
                        .desc("Enable verbose output")
                        .build()
        );
    }
    
    public ProjectHope2Command()
    {
        
        
    }
    
    @Override
    public RpgSystemDescriptor getCommandDesc()
    {
        return DESC;
    }
    
    @Override
    public MessageBuilder execCommand(String cmdLine)
    {
        MessageBuilder retVal = new MessageBuilder();
        Matcher prefixMatcher = PREFIX.matcher(cmdLine);
        if (prefixMatcher.matches())
        {
            String params = prefixMatcher.group(CMD_PARAMS);
            if (params == null || params.isEmpty())
            {
                return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
            }
            try
            {
                CommandLineParser parser = new DefaultParser();
                CommandLine cmd = parser.parse(CMD_OPTIONS, params.split(" "));
                
                if (cmd.hasOption(CMD_HELP) || (cmd.hasOption(NOTATION_PARAM) && ( cmd.hasOption(POTENTIAL_PARAM) || cmd.hasOption(THRESHOLD_PARAM))))
                {
                    return HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
                }
                
                
                Set<ProjectHope2Roll.Modifiers> mods = new HashSet<>();
                
                String p;
                String t;
                if (cmd.hasOption(CMD_VERBOSE))
                {
                    mods.add(ProjectHope2Roll.Modifiers.VERBOSE);
                }
                
                if (cmd.hasOption(NOTATION_PARAM))
                {
                    String [] n = cmd.getOptionValue(NOTATION_PARAM).split("/");
                    p = n[0];
                    t = n[1];
                }
                else
                {
                    p = cmd.getOptionValue(POTENTIAL_PARAM);
                    t = cmd.getOptionValue(THRESHOLD_PARAM);
                }
                GenericRoll roll = new ProjectHope2Roll(Integer.parseInt(p), Integer.parseInt(t), mods);
                retVal = roll.getResult();
            } 
            catch (ParseException | NumberFormatException ex)
            {
                retVal =  HelpWrapper.printHelp(prefixMatcher.group(CMD_NAME), CMD_OPTIONS, true);
            }
        }
        return retVal;
    }
    
}
