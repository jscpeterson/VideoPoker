
for f in *.png
do
mv "$f" "`echo $f | tr '[:upper:]' '[:lower:]'`"
done
