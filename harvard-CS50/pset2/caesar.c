#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include<ctype.h>

int main(int argc, string argv[])
{
    if(argc != 2)
    {
        printf("error\n");
        return 1;
    }

    int k = atoi(argv[1]);
    string s = GetString();
    
    for(int i=0, n=strlen(s); i<n; i++)
    {
        if(s[i] <= 'z' && 'a' <= s[i])
        {
            printf("%c", (char)((int)s[i]-'a'+k)%26+'a');
        }
        else if(s[i] <= 'Z' && 'A' <= s[i])
        {
            printf("%c", (char)((int)s[i]-'A'+k)%26+'A');
        }
        else
        {
            printf("%c", s[i]);
        }
    }
    printf("\n");
    
    return 0;
}
