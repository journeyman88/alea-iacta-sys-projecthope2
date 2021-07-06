# alea-iacta-sys-projecthope2
A RPG system module for Alea Iacta Est implementing Project H.O.P.E. - 2nd Edition

## Description
This command implements the dice rule system of Brass Age and can work in two modes: Notation mode and Extended mode. These will calculate successes based on potential, threshold, automatic successes and incremented results.

### Notation Mode
This is a shortcut for the extended mode,translating a single P/T notation (13/6) to -p 13 -t 6.

### Extended Mode
This mode will roll up to 10d10 - depending on the potential - then check for success against the threshold and, if the potential is >= 10 adding the relative automatic successes, and doing the dice increment on a dice according to the rules, trying to achieve the maximum number of successes.

### Roll modifiers
Passing these parameters, the associated modifier will be enabled:

* `-v` : Will enable a more verbose mode that will show a detailed version of every result obtained in the roll.

## Help print
```
Project H.O.P.E. 2nd Edition [ project-hope-2nd | ph2 ]

Usage: ph2 -n <diceNotation>
   or: ph2 -p <potentialValue> -t <thresholdValue>

Description:
This command implements the dice rule system of Brass Age and can work
in two modes: Notation mode and Extended mode. These will calculate
successes based on potential, threshold, automatic successes and
incremented results.

Options:
  -p, --potential=potentialValue
                  Potential of the dice pool. Requires threshold
  -t, --threshold=thresholdValue
                  Threshold for the dice pool between 10 and 3. Requires
                    potential
  -n, --notation=diceNotation
                  Roll in P/T notation
  -h, --help      Print the command help
  -v, --verbose   Enable verbose output
```
