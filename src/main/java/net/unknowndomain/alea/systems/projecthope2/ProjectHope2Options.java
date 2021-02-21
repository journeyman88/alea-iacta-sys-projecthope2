/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.unknowndomain.alea.systems.projecthope2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.unknowndomain.alea.systems.RpgSystemOptions;
import net.unknowndomain.alea.systems.annotations.RpgSystemData;
import net.unknowndomain.alea.systems.annotations.RpgSystemOption;


/**
 *
 * @author journeyman
 */
@RpgSystemData(bundleName = "net.unknowndomain.alea.systems.projecthope2.RpgSystemBundle")
public class ProjectHope2Options extends RpgSystemOptions
{
    @RpgSystemOption(name = "potential", shortcode = "p", description = "projecthope2.options.action", argName = "potentialValue")
    private Integer potential;
    @RpgSystemOption(name = "threshold", shortcode = "t", description = "projecthope2.options.threshold", argName = "thresholdValue")
    private Integer threshold;
    @RpgSystemOption(name = "notation", shortcode = "n", description = "projecthope2.options.notation", argName = "diceNotation")
    private String notation;
            
                        
    @Override
    public boolean isValid()
    {
        return !(
                isHelp() || 
                (notation != null && (potential != null || threshold != null)) || 
                (potential != null ^ threshold != null)
                );
    }
    
    public boolean isNotationMode()
    {
        return (notation != null && (potential == null && threshold == null));
    }
    
    public boolean isBasicMode()
    {
        return (potential != null && threshold != null);
    }

    public Integer getPotential()
    {
        int p = 0;
        if (isBasicMode())
        {
            p += potential;
        }
        if (isNotationMode())
        {
            String [] n = notation.split("/");
            p += Integer.parseInt(n[0]);
        }
        return p;
    }

    public Integer getThreshold()
    {
        int t = 0;
        if (isBasicMode())
        {
            t += threshold;
        }
        if (isNotationMode())
        {
            String [] n = notation.split("/");
            t += Integer.parseInt(n[1]);
        }
        return t;
    }

    public Collection<ProjectHope2Modifiers> getModifiers()
    {
        Set<ProjectHope2Modifiers> mods = new HashSet<>();
        if (isVerbose())
        {
            mods.add(ProjectHope2Modifiers.VERBOSE);
        }
        return mods;
    }
    
}