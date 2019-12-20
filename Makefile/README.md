# make && Makefile
## Reference
> https://makefiletutorial.com/
## Difinition
> excute shell command in order for compiler as stated on the Makefile

## Reason to use
* Saves time by automating repetitive commands.
* analyze dependent structure quickly and easy to manage.
* simple repetitive task and minimize productivity.

## Find order
```
1. GNUmakefile
2. makefile
3. Makefile
```

## Syntax
```
targets : prerequisities
    command
    command
    command
```
## example
### simple example
* execute command: 
```
make simple_test
```
* Makefile:
```
simple_test:
    @echo "this is simple test.."
```
* result:
```
this is simple test..
```
### prerequisities example
* execute command: 
```
make simple_test
```
* Makefile:
```
simple_test: other_test
    @echo "this is simple test.."

other_test:
    @echo "other test.."
```
* result:
```
other test...
this is simple test..
```

### PHONY exampele
PHONY :: set virtual name
Prevent problems if the path has the same file name.
* execute command:
```
make simple_test
make clean
```
* Makefile:
```
simple_test:
	@touch simple_test
	@touch clean
 
.PHONY: clean
clean:
	@rm -f simple_test
	@rm -f clean
```

### array variables example
* execute command:
```
make simple_test
make clean
```
* Makefile:
```
tests = test1 test2
simple_test: $(tests)
	@echo "Look at this variable: " $(tests)
	@touch simple_test

test1:
	@touch test1
test2:
	@touch test2
 
clean:
	@rm -f test1 test2 simple_test
```
### ignore example
* execute command:
```
make ignore
```
* Makefile:
```
ignore: 
	-ls -l asdklfjas.txt
	@echo "all...."

```

## function support by Makefile
### subst
```
$(subst from, to, text)
```
this function is finds 'from' in 'text' and changes 'to'.
```
input : $(subst ame, Ame, game jame)
output : gAme jAme
```

### patsubst
```
$(patsubst pattern, replacement, text)
```
this function is finds 'pattern' in 'text' and changes 'replacement'. however, it is defferent to 'subst' because it changes patterns, not text.
```
input : $(patsubst %.c,%o,x.c.c bar.c
output : x.c.o.bar.o 
```

### wildcard
```
$(wildcard ../target/*.c)
```
this function is loads files.
```
input : $(wildcard ../target/*.c)
output : ../target/main.c ../target/source.c
```

### notdir
```
$(notdir names...)
```
this function deletes the path from the name.
```
input : $(notdir forge/target.c name.c pms/fiya.mp3)
output : target.c name.c fiya.mp3
```


