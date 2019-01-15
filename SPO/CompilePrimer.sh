#! /bin/bash
# Script building tex files examples

PRIMER=$1

cd Primeri/$PRIMER/

# First compile pass 
pdflatex $PRIMER.tex
evince $PRIMER.pdf
echo "########################"
echo "First compile $PRIMER.aux"
cat $PRIMER.aux
echo "########################"

# Second compile pass
pdflatex $PRIMER.tex
evince $PRIMER.pdf
echo "########################"
echo "Second compile $PRIMER.aux"
cat $PRIMER.aux
echo "########################"

shopt -s extglob
rm !($PRIMER.tex)
shopt -u extglob