/*
 * This is sample code generated by rpcgen.
 * These are only templates and you can use them
 * as a guideline for developing your own functions.
 */

#include "rand.h"


double
rand_prog_1(char *host)
{
	CLIENT *clnt;
	void  *result_1;
	long  initialize_random_1_arg;
	double  *result_2;
	char *get_next_random_1_arg;

#ifndef	DEBUG
	clnt = clnt_create (host, RAND_PROG, RAND_VERS, "udp");
	if (clnt == NULL) {
		clnt_pcreateerror (host);
		exit (1);
	}
#endif	/* DEBUG */

	result_1 = initialize_random_1(&initialize_random_1_arg, clnt);
	if (result_1 == (void *) NULL) {
		clnt_perror (clnt, "call failed");
	}
	result_2 = get_next_random_1((void*)&get_next_random_1_arg, clnt);
	if (result_2 == (double *) NULL) {
		clnt_perror (clnt, "call failed");
	}
#ifndef	DEBUG
	clnt_destroy (clnt);
#endif	 /* DEBUG */

  return *result_2;
}


int
main (int argc, char *argv[])
{
	char *host;

	if (argc < 2) {
		printf ("usage: %s server_host\n", argv[0]);
		exit (1);
	}
	host = argv[1];
	rand_prog_1 (host);

  double x;
      int i;
      printf("\n twenty random numbers ");
      for ( i = 0; i < 20; ++i ){
              x = rand_prog_1 (host);
              //creating time delay to get different numbers
              for (j = 0; j < 5000; j++){
                continue;
              }
              printf(" %f, ", x );
      }
exit (0);
}
