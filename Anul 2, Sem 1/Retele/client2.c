#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
	int c;
	struct sockaddr_in server;
	char str[100];

	if(argc!=3)
	{
		printf("Eroare de input!\n");
		return 1;
	}

	c=socket(AF_INET,SOCK_STREAM,0);
	if(c<0)
	{
		printf("Eroare la creare socketului\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=inet_addr(argv[2]);
	if(connect(c,(struct sockaddr *) &server, sizeof(server))<0)
	{
		printf("Eroare la conectarea la server\n");
		return 1;
	}
	printf("Sirul este: \n");
	fgets(str,100,stdin);
	send(c, str, sizeof(str),0);
	int nr=0;
	recv(c, &nr,sizeof(nr),0);
	nr=ntohs(nr);
	printf("Numarul de spatii este: %d\n",nr);
	close(c);
}
