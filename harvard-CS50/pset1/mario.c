#include <stdio.h>
#include <cs50.h>

int main(void)
{
    int i,j;
    
    printf("height: ");
    int n = GetInt();
    while(n < 0 || n > 23)
    {
        printf("height: ");
        scanf("%d", &n);
    }
    
    
    for(i=0; i<n; i++)
    {
        for(j=0; j<n-i-1; j++)
        {
            printf(" ");
        }
        
        for(j=0; j<i+2; j++)
        {
            printf("#");
        }
        printf("\n");
    }
    
    return 0;
}
