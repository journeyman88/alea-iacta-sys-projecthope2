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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import net.unknowndomain.alea.random.SingleResult;
import net.unknowndomain.alea.random.SingleResultComparator;
import net.unknowndomain.alea.random.dice.DicePool;
import net.unknowndomain.alea.random.dice.bag.D10;
import net.unknowndomain.alea.roll.GenericResult;
import net.unknowndomain.alea.roll.GenericRoll;

/**
 *
 * @author journeyman
 */
public class ProjectHope2Roll implements GenericRoll
{
    
    private final DicePool<D10> dicePool;
    private final Integer potential;
    private final Integer threshold;
    private final Set<ProjectHope2Modifiers> mods;
    private final Locale lang;
    
    public ProjectHope2Roll(Integer potential, Integer threshold, Locale lang, ProjectHope2Modifiers ... mod)
    {
        this(potential, threshold, lang, Arrays.asList(mod));
    }
    
    public ProjectHope2Roll(Integer potential, Integer threshold, Locale lang, Collection<ProjectHope2Modifiers> mod)
    {
        this.mods = new HashSet<>();
        if (mod != null)
        {
            this.mods.addAll(mod);
        }
        int dice = potential;
        if (potential > 10)
        {
            dice = 10;
        }
        if (potential < 1 )
        {
            potential = 1;
            threshold = 10;
        }
        if (threshold < 3)
        {
            threshold = 3;
        }
        if (threshold > 10)
        {
            threshold = 10;
        }
        this.dicePool = new DicePool<>(D10.INSTANCE, dice);
        this.potential = potential;
        this.threshold = threshold;
        this.lang = lang;
    }
    
    @Override
    public GenericResult getResult()
    {
        List<SingleResult<Integer>> resultsPool = this.dicePool.getResults();
        List<SingleResult<Integer>> res = new ArrayList<>();
        res.addAll(resultsPool);
        ProjectHope2Results results = buildResults(res);
        results.setVerbose(mods.contains(ProjectHope2Modifiers.VERBOSE));
        results.setLang(lang);
        return results;
    }
    
    
    private ProjectHope2Results buildResults(List<SingleResult<Integer>> res)
    {
        SingleResultComparator comp = new SingleResultComparator();
        res.sort(comp);
        ProjectHope2Results results = new ProjectHope2Results(res);
        int auto = this.potential / 10;
        List<SingleResult<Integer>> left = new ArrayList<>();
        results.addAutoSuccesses(auto);
        while(!res.isEmpty())
        {
            SingleResult<Integer> value = res.remove(0);
            if (value.getValue() >= this.threshold)
            {
                results.addSuccess(value);
            }
            else
            {
                left.add(value);
            }
        }
        if ((this.potential > 10) && (!left.isEmpty()))
        {
            results.setIncrement(true);
            int module = this.potential % 10;
            if (module > left.size())
            {
                module = left.size();
            }
            SingleResult<Integer> value = left.remove(module-1);
            results.setOldValue(value);
            results.setNewValue(new SingleResult<>("d10 + 3", value.getValue() + 3));
            if (results.getNewValue().getValue() >= this.threshold)
            {
                results.addSuccess(results.getNewValue());
            }
            else
            {
                left.add(results.getNewValue());
            }
        }
        results.setLeftovers(left);
        return results;
    }
}
