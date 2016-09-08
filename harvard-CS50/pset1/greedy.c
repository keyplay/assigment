#include <stdio.h>
#include <cs50.h>
#include <math.h>

int main(void)
{
    printf("O hai! How much change is owed?\n");
    float money = GetFloat();
    while(100*money<0)
    {
        printf("How much change is owed?\n");
        money = GetFloat();
    }
    int count = 0;
    int coin[4] = {25, 10, 5, 1};
    money *= 100;
    
    for(int i=0; i<4; i++)
    {
        while(round(money-coin[i])>=0)
        {
            money -= coin[i];
            count++;
        }
    }
    
    printf("%d\n", count);
    
}
