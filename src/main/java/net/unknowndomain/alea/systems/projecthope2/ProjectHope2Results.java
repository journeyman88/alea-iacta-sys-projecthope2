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
import java.util.Collections;
import java.util.List;
import net.unknowndomain.alea.messages.MsgBuilder;
import net.unknowndomain.alea.roll.GenericResult;

/**
 *
 * @author journeyman
 */
public class ProjectHope2Results extends GenericResult
{
    private final List<Integer> results;
    private int successes = 0;
    private Integer oldValue;
    private Integer newValue;
    private List<Integer> successDice = new ArrayList<>();
    private List<Integer> leftovers;
    private boolean increment = false;
    
    public ProjectHope2Results(List<Integer> results)
    {
        List<Integer> tmp = new ArrayList<>(results.size());
        tmp.addAll(results);
        this.results = Collections.unmodifiableList(tmp);
    }
    
    public void addSuccess(Integer dice)
    {
        addSuccess(1, dice);
    }
    
    public void addAutoSuccesses(int value)
    {
        addSuccess(value, null);
    }
    
    
    private void addSuccess(int value, Integer dice)
    {
        if (dice != null)
        {
            successDice.add(dice);
        }
        successes += value;
    }

    public int getSuccesses()
    {
        return successes;
    }

    public List<Integer> getResults()
    {
        return results;
    }

    public Integer getNewValue()
    {
        return newValue;
    }

    public void setNewValue(Integer newValue)
    {
        this.newValue = newValue;
    }

    public Integer getOldValue()
    {
        return oldValue;
    }

    public void setOldValue(Integer oldValue)
    {
        this.oldValue = oldValue;
    }

    public List<Integer> getSuccessDice()
    {
        return successDice;
    }

    public List<Integer> getLeftovers()
    {
        return leftovers;
    }

    public void setLeftovers(List<Integer> leftovers)
    {
        this.leftovers = leftovers;
    }

    @Override
    protected void formatResults(MsgBuilder messageBuilder, boolean verbose, int indentValue)
    {
        messageBuilder.append("Successes: ").append(getSuccesses()).appendNewLine();
        messageBuilder.append("Leftovers: ").append(getLeftovers().size()).appendNewLine();
        if (verbose)
        {
            messageBuilder.append("Automatic: ").append(getSuccesses() - getSuccessDice().size()).appendNewLine();
            if (increment)
            {
                messageBuilder.append("Increment: true (").append(getOldValue()).append(" => ").append(getNewValue()).append(")").appendNewLine();
            }
            messageBuilder.append("Roll ID: ").append(getUuid()).appendNewLine();
            messageBuilder.append("Results: ").append(" [ ");
            for (Integer t : getResults())
            {
                messageBuilder.append(t).append(" ");
            }
            messageBuilder.append("]").appendNewLine();
        }
    }

    public boolean isIncrement()
    {
        return increment;
    }

    public void setIncrement(boolean increment)
    {
        this.increment = increment;
    }

}
