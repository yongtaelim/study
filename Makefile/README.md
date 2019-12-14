# make && Makefile
## Reference
> https://makefiletutorial.com/
## Difinition
> excute shell command in order for compiler as stated on the Makefile

## Reason to use
* Saves time by automating repetitive commands.
* analyze dependent structure quickly and easy to manage.
* simple repetitive task and minimize productivity.

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


