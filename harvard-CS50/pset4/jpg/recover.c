/**
 * recover.c
 *
 * Computer Science 50
 * Problem Set 4
 *
 * Recovers JPEGs from a forensic image.
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>
#include <cs50.h>

#define NUM_JPG 15

typedef uint8_t  BYTE;

bool ifjpg(BYTE* buffer);

int main(int argc, char* argv[])
{
    // open the data source
    FILE* file = fopen("card.raw", "r");
    if (file == NULL)
    {
        printf("Could not open card.raw.\n");
        return 1;
    }
    
    //read data 512 bytes by 512 bytes
    BYTE* buffer = malloc(512*sizeof(BYTE));
    int count = 0;
    while(fread(buffer, 512, 1, file) > 0)//while(count<NUM_JPG+1)    //while(fread(buffer, 512, 1, file) > 0)
    {
        //fread(buffer, 512, 1, file);
        if(ifjpg(buffer))
        {
            char* name = malloc(3*sizeof(char));
            sprintf(name, "%03d.jpg", count);
            
            // open output file
            FILE* outimage = fopen(name, "w");
            if (outimage == NULL)
            {
                fclose(file);
                fprintf(stderr, "Could not create %s.\n", name);
                return 2;
            }
            
            int n = 0;
            do
            {
                if(count == NUM_JPG)
                {
                    n++;
                    if(buffer[0]==0x00)
                    {
                        break;
                    }
                }
                fwrite(buffer, 512, 1, outimage);
                fread(buffer, 512, 1, file);
            }while(!ifjpg(buffer));
            
            if(ifjpg(buffer))
            {
                fseek(file, -512, SEEK_CUR);
            }
            
            fclose(outimage);
            count += 1;
        }
        
    }
    
    free(buffer);
    fclose(file);
    
    return 0;
}

bool ifjpg(BYTE* buffer)
{
    if(buffer[0] == 0xff && buffer[1] == 0xd8 && buffer[2] == 0xff)
    {
        if((buffer[3] & 0xf0) == 0xe0)
        {
            return true;
        }
    }
    
    return false;
}
