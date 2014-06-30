import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONException;
import org.json.JSONArray;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.*;

public class Final_Project {

    private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);    
    }
    return sb.toString();
  }

  public static JSONArray readJsonFromUrl(String url) throws IOException, JSONException {
    InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONArray json = new JSONArray(jsonText);     
      return json;
    } finally {
      is.close();
    }
  }
  
  public static int factorial(int p) /* 函式定義 */
  {
  int result = 1;
  for(int count=1;count<=p;count++)
  result = result * count;
  return result;
  }

  public static void main(String[] args) throws IOException, JSONException {
	int jsonlong=0,Datalong=0;
	int n=Integer.valueOf(args[2]);
    int ComNum;
    FileWriter fw = new FileWriter("output.txt");
    JSONArray json = readJsonFromUrl(args[0]);
    Datalong=json.length();
    jsonlong=json.getJSONObject(0).length();
    int m=jsonlong;
	int temp[]=new int[3]; 
	temp[0] = factorial(m); // 計算 m! 的值
	temp[1] = factorial(n); // 計算 n! 的值
	temp[2] = factorial(m-n); // 計算 (m-n)! 的值
	ComNum = (temp[0])/(temp[1]*temp[2]); //C(m,n)=(m!)/(n!*(m-n)!)
    String [] Str= new String[]{"鄉鎮市區","交易標的","土地區段位置或建物區門牌","土地移轉總面積平方公尺","都市土地使用分區","非都市土地使用分區","非都市土地使用編定","交易年月","交易筆棟數","移轉層次","總樓層數","建物型態","主要用途","主要建材","建築完成年月","建物移轉總面積平方公尺","建物現況格局-房","建物現況格局-廳","建物現況格局-衛","建物現況格局-隔間","有無管理組織","總價元","單價每平方公尺","車位類別","車位移轉總面積平方公尺","車位總價元"};
    String [] Title=new String[jsonlong];
    String [][][] ComData= new String[ComNum][Datalong+1][8];
    int [][] NumOfComData= new int[ComNum][Datalong+1];
    int NumOfTotalData=0; 
    int i,j=0,p,q,numSame=0;    
    for(i=0;i<26;i++)
    {
    	if(json.getJSONObject(0).has(Str[i]))
    	{
    		Title[j]=Str[i];
    		j++;
    	}
    }  
    int StartFlag=0,CombiFlag=0,a,b,tag1,tag2;
    int [] tm = new int[ComNum+1000];
    int k,count,intTemp=0,sp=0,tt=0;	
    String stringTemp= new String();
    int [] max =new int[ComNum];  
    int SumOfTotalData=0;
    int countt=0;

   if(n==2)
   {
    for(i=0;i<jsonlong;i++)
    {
    	for(j=i+1;j<jsonlong;j++)
    	{
    		for(a=0;a<Datalong;a++)
    		{
    			if(String.valueOf(json.getJSONObject(a).get(Title[i])).equals("")||String.valueOf(json.getJSONObject(a).get(Title[j])).equals(""))
    			{
    				continue;
    			}  			
		    		if(StartFlag==0)
		    		{
		    			ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
		    			ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
		    			ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
		    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
		    			NumOfComData[CombiFlag][tm[CombiFlag]]++;
		    			StartFlag=1;
		    			tm[CombiFlag]++;
		    			NumOfTotalData++;
		    		}
		    		else
		    		{
		    			for(tag1=a-1;tag1>=0;tag1--)
		    			{
		    				if(String.valueOf(json.getJSONObject(a).get(Title[i])).equals(ComData[CombiFlag][tag1][1]))
		    				{
		    					if(String.valueOf(json.getJSONObject(a).get(Title[j])).equals(ComData[CombiFlag][tag1][3]))
		    					{
		    						NumOfComData[CombiFlag][tag1]++;
		    						break;
		    					}
		    					else if(tag1==0)
		    					{
		    						ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
		    						ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
		    						ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
		    		    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
		    		    			NumOfComData[CombiFlag][tm[CombiFlag]]++;		    		    			
		    		    			tm[CombiFlag]++;
		    		    			NumOfTotalData++;
		    					}
		    					
		    				}
		    				else if(tag1==0)
		    				{
		    					ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
		    					ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
		    					ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
				    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
				    			NumOfComData[CombiFlag][tm[CombiFlag]]++;				    			
				    			tm[CombiFlag]++;
				    			NumOfTotalData++;
		    				}
		    			}		    			
		    		}   			
    		}
    		StartFlag=0;
    		CombiFlag++;
    	}
    }
    for(i=0;i<ComNum;i++)
    {
    	SumOfTotalData=SumOfTotalData+tm[i]+1;
    }
   
    for(k=0;k<SumOfTotalData;k++)
    {
    	sp=1;
    	count=1;
    	for(i=ComNum-1;i>=0;i--)
    	{  		
    		if(count==SumOfTotalData-k)
			{
    			break;
			}   		
    		for(j=tm[i];j>=0;j--)
    		{
    			if(count==SumOfTotalData-k)
    			{
    				break;
    			}    			
    			if(j==0)
    			{
    				if(NumOfComData[i][j]>NumOfComData[i-1][tm[i-1]])
    				{
    					intTemp=NumOfComData[i][j];
	    				NumOfComData[i][j]=NumOfComData[i-1][tm[i-1]];
	    				NumOfComData[i-1][tm[i-1]]=intTemp;
	    				
	    				stringTemp=ComData[i][j][0];
	    				ComData[i][j][0]=ComData[i-1][tm[i-1]][0];
	    				ComData[i-1][tm[i-1]][0]=stringTemp;
	    				
	    				stringTemp=ComData[i][j][1];
	    				ComData[i][j][1]=ComData[i-1][tm[i-1]][1];
	    				ComData[i-1][tm[i-1]][1]=stringTemp;
	    				
	    				stringTemp=ComData[i][j][2];
	    				ComData[i][j][2]=ComData[i-1][tm[i-1]][2];
	    				ComData[i-1][tm[i-1]][2]=stringTemp;
	    				
	    				stringTemp=ComData[i][j][3];
	    				ComData[i][j][3]=ComData[i-1][tm[i-1]][3];
	    				ComData[i-1][tm[i-1]][3]=stringTemp;

	    				if(tt==0)
	    				{
	    					max[i]++;
	    				}	    				
	    				sp=0;
    				}
    			}   			
    			else 
    			{
    				if(NumOfComData[i][j]>NumOfComData[i][j-1])
    				{
	    				intTemp=NumOfComData[i][j];
	    				NumOfComData[i][j]=NumOfComData[i][j-1];
	    				NumOfComData[i][j-1]=intTemp;
	    				
	    				stringTemp=ComData[i][j][0];
	    				ComData[i][j][0]=ComData[i][j-1][0];
	    				ComData[i][j-1][0]=stringTemp;
	    				
	    				stringTemp=ComData[i][j][1];
	    				ComData[i][j][1]=ComData[i][j-1][1];
	    				ComData[i][j-1][1]=stringTemp;
	    				
	    				stringTemp=ComData[i][j][2];
	    				ComData[i][j][2]=ComData[i][j-1][2];
	    				ComData[i][j-1][2]=stringTemp;
	    				
	    				stringTemp=ComData[i][j][3];
	    				ComData[i][j][3]=ComData[i][j-1][3];
	    				ComData[i][j-1][3]=stringTemp;

	    				if(tt==0)
	    				{
	    					max[i]++;	    				
	    				}	    				
	    				sp=0;
    				}
    			}
    			count++;
    		}  		
    	}
    	tt=1;
    	if(sp==1) break;
    }
    countt=0;
    for(i=0;i<ComNum;i++)
    {    	
    	if(countt>=Integer.valueOf(args[1]))
		{
    		break;
		}
    	
    	for(j=0;j<=tm[i];j++)
    	{
    		if(countt>=Integer.valueOf(args[1])&&numSame!=NumOfComData[i][j])
			{
    			break;
			}
    		
    		System.out.println(ComData[i][j][0]+":"+ComData[i][j][1]+","+ComData[i][j][2]+":"+ComData[i][j][3]+";"+NumOfComData[i][j]);
    		fw.write(ComData[i][j][0]+":"+ComData[i][j][1]+","+ComData[i][j][2]+":"+ComData[i][j][3]+";"+NumOfComData[i][j]+"\n");
    		numSame=NumOfComData[i][j];
    		countt++;
    	}
    }
   }
   else if(n==3)
   {	   
	   for(i=0;i<jsonlong;i++)
	    {
	    	for(j=i+1;j<jsonlong;j++)
	    	{
	    		for(p=j+1;p<jsonlong;p++)
	    		{
		    		for(a=0;a<Datalong;a++)
		    		{
		    			if(String.valueOf(json.getJSONObject(a).get(Title[i])).equals("")||String.valueOf(json.getJSONObject(a).get(Title[j])).equals("")||String.valueOf(json.getJSONObject(a).get(Title[p])).equals(""))
		    			{
		    				continue;
		    			}
		    			
				    	if(StartFlag==0)
				    	{	
				    		ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
				    		ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
				    		ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
				    		ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
				    		ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
				    		ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
				    		NumOfComData[CombiFlag][tm[CombiFlag]]++;
				    		StartFlag=1;
				    		tm[CombiFlag]++;
				    		NumOfTotalData++;
				    	}
				    	else
				    	{
				    		for(tag1=a-1;tag1>=0;tag1--)
				    		{
				    			if(String.valueOf(json.getJSONObject(a).get(Title[i])).equals(ComData[CombiFlag][tag1][1]))
				    			{		   					
				    				if(String.valueOf(json.getJSONObject(a).get(Title[j])).equals(ComData[CombiFlag][tag1][3]))
				    				{
				    					if(String.valueOf(json.getJSONObject(a).get(Title[p])).equals(ComData[CombiFlag][tag1][5]))
				    					{
				    						NumOfComData[CombiFlag][tag1]++;
				    						break;
				    					}		    					
					    				else if(tag1==0)
					    				{
					    					ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
					    					ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
					    					ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
					    		    		ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
					    		    		ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
					    					ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
					    		    		NumOfComData[CombiFlag][tm[CombiFlag]]++;		    		    			
					    		    		tm[CombiFlag]++;
					    		    		NumOfTotalData++;
					    				}
				    				}
				    				else if(tag1==0)
				    				{
				    					ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
				    					ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
				    					ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
				    		    		ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
				    		    		ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
				    					ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
				    		    		NumOfComData[CombiFlag][tm[CombiFlag]]++;		    		    			
				    		    		tm[CombiFlag]++;
				    		    		NumOfTotalData++;
				    				}			    				
				    			}
				    			else if(tag1==0)
				    			{
				    				ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
				    				ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
				    				ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
						    		ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
						    		ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
				    				ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
						    		NumOfComData[CombiFlag][tm[CombiFlag]]++;				    			
						    		tm[CombiFlag]++;
						    		NumOfTotalData++;
				    			}
				    		}			    			
				    	}	    			
		    		}
		    		StartFlag=0;
		    		CombiFlag++;
	    		}
	    	}
	    }
	    for(i=0;i<ComNum;i++)
	    {
	    	SumOfTotalData=SumOfTotalData+tm[i]+1;
	    }
	    for(k=0;k<SumOfTotalData;k++)
	    {
	    	sp=1;
	    	count=1;
	    	for(i=ComNum-1;i>=0;i--)
	    	{	    		
	    		if(count==SumOfTotalData-k)
				{
	    			break;
				}    		
	    		for(j=tm[i];j>=0;j--)
	    		{
	    			if(count==SumOfTotalData-k)
	    			{
	    				break;
	    			}	    			
	    			if(j==0)
	    			{
	    				if(NumOfComData[i][j]>NumOfComData[i-1][tm[i-1]])
	    				{
	    					intTemp=NumOfComData[i][j];
		    				NumOfComData[i][j]=NumOfComData[i-1][tm[i-1]];
		    				NumOfComData[i-1][tm[i-1]]=intTemp;
		    				
		    				stringTemp=ComData[i][j][0];
		    				ComData[i][j][0]=ComData[i-1][tm[i-1]][0];
		    				ComData[i-1][tm[i-1]][0]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][1];
		    				ComData[i][j][1]=ComData[i-1][tm[i-1]][1];
		    				ComData[i-1][tm[i-1]][1]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][2];
		    				ComData[i][j][2]=ComData[i-1][tm[i-1]][2];
		    				ComData[i-1][tm[i-1]][2]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][3];
		    				ComData[i][j][3]=ComData[i-1][tm[i-1]][3];
		    				ComData[i-1][tm[i-1]][3]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][4];
		    				ComData[i][j][4]=ComData[i-1][tm[i-1]][4];
		    				ComData[i-1][tm[i-1]][4]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][5];
		    				ComData[i][j][5]=ComData[i-1][tm[i-1]][5];
		    				ComData[i-1][tm[i-1]][5]=stringTemp;
		    				
		    				if(tt==0)
		    				{
		    					max[i]++;
		    				}		    				
		    				sp=0;
	    				}
	    			}   			
	    			else 
	    			{
	    				if(NumOfComData[i][j]>NumOfComData[i][j-1])
	    				{
		    				intTemp=NumOfComData[i][j];
		    				NumOfComData[i][j]=NumOfComData[i][j-1];
		    				NumOfComData[i][j-1]=intTemp;
		    				
		    				stringTemp=ComData[i][j][0];
		    				ComData[i][j][0]=ComData[i][j-1][0];
		    				ComData[i][j-1][0]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][1];
		    				ComData[i][j][1]=ComData[i][j-1][1];
		    				ComData[i][j-1][1]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][2];
		    				ComData[i][j][2]=ComData[i][j-1][2];
		    				ComData[i][j-1][2]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][3];
		    				ComData[i][j][3]=ComData[i][j-1][3];
		    				ComData[i][j-1][3]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][4];
		    				ComData[i][j][4]=ComData[i][j-1][4];
		    				ComData[i][j-1][4]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][5];
		    				ComData[i][j][5]=ComData[i][j-1][5];
		    				ComData[i][j-1][5]=stringTemp;
		    				
		    				if(tt==0)
		    				{
		    					max[i]++;	    				
		    				}		    				
		    				sp=0;
	    				}
	    			}
	    			count++;
	    		}    		
	    	}
	    	tt=1;
	    	if(sp==1) break;
	    }
	    countt=0;
	    for(i=0;i<ComNum;i++)
	    {    	
	    	if(countt==Integer.valueOf(args[1]))
			{
	    		break;
			}	    	
	    	for(j=0;j<=tm[i];j++)
	    	{
	    		if(countt>=Integer.valueOf(args[1])&&numSame!=NumOfComData[i][j])
				{
	    			break;
				}	    		
	    		System.out.println(ComData[i][j][0]+":"+ComData[i][j][1]+","+ComData[i][j][2]+":"+ComData[i][j][3]+","+ComData[i][j][4]+":"+ComData[i][j][5]+";"+NumOfComData[i][j]);
	    		fw.write(ComData[i][j][0]+":"+ComData[i][j][1]+","+ComData[i][j][2]+":"+ComData[i][j][3]+","+ComData[i][j][4]+":"+ComData[i][j][5]+";"+NumOfComData[i][j]+"\n");
	    		numSame=NumOfComData[i][j];
	    		countt++;
	    	}
	    }
   
   }
   else if(n==4)
   {   
	   for(i=0;i<jsonlong;i++)
	    {
	    	for(j=i+1;j<jsonlong;j++)
	    	{
	    		for(p=j+1;p<jsonlong;p++)
	    		{
	    			for(q=p+1;q<jsonlong;q++)
		    		{
			    		for(a=0;a<Datalong;a++)
			    		{
			    			
			    			if(String.valueOf(json.getJSONObject(a).get(Title[i])).equals("")||String.valueOf(json.getJSONObject(a).get(Title[j])).equals("")||String.valueOf(json.getJSONObject(a).get(Title[p])).equals("")||String.valueOf(json.getJSONObject(a).get(Title[q])).equals(""))
			    			{
			    				continue;
			    			}		    			
					    		if(StartFlag==0)
					    		{	
					    			ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
					    			ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
					    			ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
					    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
					    			ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
					    			ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
					    			ComData[CombiFlag][tm[CombiFlag]][6]= Title[q];
					    			ComData[CombiFlag][tm[CombiFlag]][7]= String.valueOf(json.getJSONObject(a).get(Title[q]));
					    			NumOfComData[CombiFlag][tm[CombiFlag]]++;
					    			StartFlag=1;
					    			tm[CombiFlag]++;
					    			NumOfTotalData++;
					    		}
					    		else
					    		{
					    			for(tag1=a-1;tag1>=0;tag1--)
					    			{
					    				if(String.valueOf(json.getJSONObject(a).get(Title[i])).equals(ComData[CombiFlag][tag1][1]))
					    				{				   					
					    					if(String.valueOf(json.getJSONObject(a).get(Title[j])).equals(ComData[CombiFlag][tag1][3]))
					    					{
					    						if(String.valueOf(json.getJSONObject(a).get(Title[p])).equals(ComData[CombiFlag][tag1][5]))
					    						{
					    							if(String.valueOf(json.getJSONObject(a).get(Title[q])).equals(ComData[CombiFlag][tag1][7]))
					    							{
							    						NumOfComData[CombiFlag][tag1]++;
							    						break;
					    							}
					    							else if(tag1==0)
							    					{
							    						ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
							    						ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
							    						ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
							    		    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
							    		    			ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
							    						ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
							    						ComData[CombiFlag][tm[CombiFlag]][6]= Title[q];
							    						ComData[CombiFlag][tm[CombiFlag]][7]= String.valueOf(json.getJSONObject(a).get(Title[q]));
							    		    			NumOfComData[CombiFlag][tm[CombiFlag]]++;		    		    			
							    		    			tm[CombiFlag]++;
							    		    			NumOfTotalData++;
							    					}
					    						}					    					
						    					else if(tag1==0)
						    					{
						    						ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
						    						ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
						    						ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
						    		    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
						    		    			ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
						    						ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
						    						ComData[CombiFlag][tm[CombiFlag]][6]= Title[q];
						    						ComData[CombiFlag][tm[CombiFlag]][7]= String.valueOf(json.getJSONObject(a).get(Title[q]));
						    		    			NumOfComData[CombiFlag][tm[CombiFlag]]++;		    		    			
						    		    			tm[CombiFlag]++;
						    		    			NumOfTotalData++;
						    					}
					    					}
					    					else if(tag1==0)
					    					{
					    						ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
					    						ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
					    						ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
					    		    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
					    		    			ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
					    						ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
					    						ComData[CombiFlag][tm[CombiFlag]][6]= Title[q];
					    						ComData[CombiFlag][tm[CombiFlag]][7]= String.valueOf(json.getJSONObject(a).get(Title[q]));
					    		    			NumOfComData[CombiFlag][tm[CombiFlag]]++;		    		    			
					    		    			tm[CombiFlag]++;
					    		    			NumOfTotalData++;
					    					}					    				
					    				}
					    				else if(tag1==0)
					    				{
					    					ComData[CombiFlag][tm[CombiFlag]][0]= Title[i];
					    					ComData[CombiFlag][tm[CombiFlag]][1]= String.valueOf(json.getJSONObject(a).get(Title[i]));
					    					ComData[CombiFlag][tm[CombiFlag]][2]= Title[j];
							    			ComData[CombiFlag][tm[CombiFlag]][3]= String.valueOf(json.getJSONObject(a).get(Title[j]));
							    			ComData[CombiFlag][tm[CombiFlag]][4]= Title[p];
					    					ComData[CombiFlag][tm[CombiFlag]][5]= String.valueOf(json.getJSONObject(a).get(Title[p]));
					    					ComData[CombiFlag][tm[CombiFlag]][6]= Title[q];
					    					ComData[CombiFlag][tm[CombiFlag]][7]= String.valueOf(json.getJSONObject(a).get(Title[q]));
							    			NumOfComData[CombiFlag][tm[CombiFlag]]++;				    			
							    			tm[CombiFlag]++;
							    			NumOfTotalData++;
					    				}
					    			}					    			
					    		}		    			
			    		}
			    		StartFlag=0;
			    		CombiFlag++;
				  	}
	    		}
	    	}
	    }
	    for(i=0;i<ComNum;i++)
	    {
	    	SumOfTotalData=SumOfTotalData+tm[i]+1;
	    }	    
	    for(k=0;k<SumOfTotalData;k++)
	    {
	    	sp=1;
	    	count=1;
	    	for(i=ComNum-1;i>=0;i--)
	    	{	    		
	    		if(count==SumOfTotalData-k)
				{
	    			break;
				}   		
	    		for(j=tm[i];j>=0;j--)
	    		{
	    			if(count==SumOfTotalData-k)
	    			{
	    				break;
	    			}    			
	    			if(j==0)
	    			{
	    				if(NumOfComData[i][j]>NumOfComData[i-1][tm[i-1]])
	    				{
	    					intTemp=NumOfComData[i][j];
		    				NumOfComData[i][j]=NumOfComData[i-1][tm[i-1]];
		    				NumOfComData[i-1][tm[i-1]]=intTemp;
		    				
		    				stringTemp=ComData[i][j][0];
		    				ComData[i][j][0]=ComData[i-1][tm[i-1]][0];
		    				ComData[i-1][tm[i-1]][0]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][1];
		    				ComData[i][j][1]=ComData[i-1][tm[i-1]][1];
		    				ComData[i-1][tm[i-1]][1]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][2];
		    				ComData[i][j][2]=ComData[i-1][tm[i-1]][2];
		    				ComData[i-1][tm[i-1]][2]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][3];
		    				ComData[i][j][3]=ComData[i-1][tm[i-1]][3];
		    				ComData[i-1][tm[i-1]][3]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][4];
		    				ComData[i][j][4]=ComData[i-1][tm[i-1]][4];
		    				ComData[i-1][tm[i-1]][4]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][5];
		    				ComData[i][j][5]=ComData[i-1][tm[i-1]][5];
		    				ComData[i-1][tm[i-1]][5]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][6];
		    				ComData[i][j][6]=ComData[i-1][tm[i-1]][6];
		    				ComData[i-1][tm[i-1]][6]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][7];
		    				ComData[i][j][7]=ComData[i-1][tm[i-1]][7];
		    				ComData[i-1][tm[i-1]][7]=stringTemp;
		    				
		    				if(tt==0)
		    				{
		    					max[i]++;
		    				}    				
		    				sp=0;
	    				}
	    			}   			
	    			else 
	    			{
	    				if(NumOfComData[i][j]>NumOfComData[i][j-1])
	    				{
		    				intTemp=NumOfComData[i][j];
		    				NumOfComData[i][j]=NumOfComData[i][j-1];
		    				NumOfComData[i][j-1]=intTemp;
		    				
		    				stringTemp=ComData[i][j][0];
		    				ComData[i][j][0]=ComData[i][j-1][0];
		    				ComData[i][j-1][0]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][1];
		    				ComData[i][j][1]=ComData[i][j-1][1];
		    				ComData[i][j-1][1]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][2];
		    				ComData[i][j][2]=ComData[i][j-1][2];
		    				ComData[i][j-1][2]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][3];
		    				ComData[i][j][3]=ComData[i][j-1][3];
		    				ComData[i][j-1][3]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][4];
		    				ComData[i][j][4]=ComData[i][j-1][4];
		    				ComData[i][j-1][4]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][5];
		    				ComData[i][j][5]=ComData[i][j-1][5];
		    				ComData[i][j-1][5]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][6];
		    				ComData[i][j][6]=ComData[i][j-1][6];
		    				ComData[i][j-1][6]=stringTemp;
		    				
		    				stringTemp=ComData[i][j][7];
		    				ComData[i][j][7]=ComData[i][j-1][7];
		    				ComData[i][j-1][7]=stringTemp;
		    				
		    				if(tt==0)
		    				{
		    					max[i]++;	    				
		    				}			
		    				sp=0;
	    				}
	    			}
	    			count++;
	    		} 		
	    	}
	    	tt=1;
	    	if(sp==1) break;
	    }
	    countt=0;
	    for(i=0;i<ComNum;i++)
	    {    	
	    	if(countt==Integer.valueOf(args[1]))
			{
	    		break;
			}    	
	    	for(j=0;j<=tm[i];j++)
	    	{
	    		if(countt>=Integer.valueOf(args[1])&&numSame!=NumOfComData[i][j])
				{
	    			break;
				}   		
	    		System.out.println(ComData[i][j][0]+":"+ComData[i][j][1]+","+ComData[i][j][2]+":"+ComData[i][j][3]+","+ComData[i][j][4]+":"+ComData[i][j][5]+","+ComData[i][j][6]+":"+ComData[i][j][7]+";"+NumOfComData[i][j]);
	    		fw.write(ComData[i][j][0]+":"+ComData[i][j][1]+","+ComData[i][j][2]+":"+ComData[i][j][3]+","+ComData[i][j][4]+":"+ComData[i][j][5]+","+ComData[i][j][6]+":"+ComData[i][j][7]+";"+NumOfComData[i][j]+"\n");
	    		numSame=NumOfComData[i][j];
	    		countt++;
	    	}
	    }   
   }
   fw.flush();
   fw.close();
  }
}
