#include "types.h"
#include "stat.h"
#include "user.h"
#include "fcntl.h"

char buf[512];

int
main(int argc, char *argv[])
{
  int fd0, fd1, n;

  if(argc < 3){
    printf(1, "Need at least 2 arguments!\n");
    exit();
  }

  if((fd0 = open(argv[1], O_RDONLY)) < 0){
    printf(1, "cp: cannot open %s\n", argv[1]);
    exit();
  }
  
  for (i = 0; i < argc; i++){
    if((fd1 = open(argv[i], O_CREATE|O_RDWR)) < 0){
      printf(1, "cp: cannot open %s\n", argv[i]);
      exit();
    }
    while ( (n = read (fd0, buf, sizeof(buf))) > 0 ){
    write(fd1, buf, n);
    }
    close(fd1);
  }
  
  while ( (n = read (fd0, buf, sizeof(buf))) > 0 ){
      write(fd1, buf, n);
  }
  
  close(fd0);
  exit();
}
