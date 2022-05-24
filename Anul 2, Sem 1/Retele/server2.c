#include <sys/types.h>
#include <sys/socket.h>
#include <stdio.h>
#include <netinet/in.h>
#include <netinet/ip.h>
#include <string.h>
#include <stdlib.h>

int main(int argc, char* argv[])
{
	int s;
	struct sockaddr_in server,client;
	int c,l;

	if(argc!=2)
	{
		printf("Eroare la input\n");
		return 1;
	}

	s=socket(AF_INET,SOCK_STREAM,0);
	if(s<0)
	{
		printf("Eroare la crearea socketului server\n");
		return 1;
	}

	memset(&server,0,sizeof(server));
	server.sin_port=htons(atoi(argv[1]));
	server.sin_family=AF_INET;
	server.sin_addr.s_addr=INADDR_ANY;

	if(bind(s,(struct sockadder *)&server,sizeof(server))<0)
	{
		printf("Eroare la bind\n");
		return 1;
	}

	listen(s,5);

	l=sizeof(client);
	memset(&client, 0, sizeof(client));

	while(1)
	{
		c = accept(s, (struct sockaddr *) &client, &l);
		char str[100];
		printf("S-a conectat un client.\n");
		read(c, str, sizeof(str));
		int i=0;
		int nr=0;
		int l_str=strlen(str);
		for(i=0;i<l_str;i++)
			if(str[i]==' ') nr++;
		nr=htons(nr);
		send(c, &nr, sizeof(nr),0);
		close(c);
	}

}
