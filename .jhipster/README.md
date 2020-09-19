TIP: to regenerate all your entities at once, you can use the following commands (remove the --force to have questions asked when files have changed).

Linux & Mac:
```
for f in `ls .jhipster`; do jhipster entity ${f%.*} --force ; done
```
 
Windows: 
```
for %f in (.jhipster/*) do jhipster entity %~nf --force
```
