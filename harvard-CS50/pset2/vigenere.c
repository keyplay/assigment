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
    
    string keys = argv[1];
    int k[strlen(keys)];
    
    for(int i=0, n=strlen(keys); i<n; i++)
    {
        if(!isalpha(keys[i]))
        {
            printf("error\n");
            return 1;
        }
        k[i] = tolower(keys[i])-'a';
    }
    
    string s = GetString();
    int index;
    int count=0;
    for(int i=0, n=strlen(s); i<n; i++)
    {
        index = count % strlen(keys);
        if(s[i] <= 'z' && 'a' <= s[i])
        {
            count++;
            printf("%c", (char)((int)s[i]-'a'+k[index])%26+'a');
        }
        else if(s[i] <= 'Z' && 'A' <= s[i])
        {
            count++;
            printf("%c", (char)((int)s[i]-'A'+k[index])%26+'A');
        }
        else
        {
            printf("%c", s[i]);
        }
    }
    
    printf("\n");
    
    return 0;
}
