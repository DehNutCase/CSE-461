/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "rand.h"
#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int seed = 0;

void *
initialize_random_1_svc(long *argp, struct svc_req *rqstp)
{
  static char * result;
  seed += time(0) + 1; //we need to change the seed every time this is called
  srand((unsigned) seed);

  return (void *) &result;
}

double *
get_next_random_1_svc(void *argp, struct svc_req *rqstp)
{
  static double  result;

  result = (double)rand()/RAND_MAX*1.0;

  return &result;
}
