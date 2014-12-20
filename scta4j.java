import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

	/**　入出力設定　**/
class setFrame extends Frame
{
	private int PV=8;
	private Checkbox[][] cb=new Checkbox[PV+1][PV+1];
	private CheckboxGroup[] cg=new CheckboxGroup[PV+1];
	private Checkbox[] cbset=new Checkbox[6];
	private Button b1,b2;
	private Panel p,p0,p1,p2;
	public int vn=8;
	public int[] xn=new int[PV+1];
	public boolean[] set={true,true,true,true,true,true};

	/*　出力設定、デフォルトはすべてTrue　*/
	public setFrame()
	{
		setTitle("Setting");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
	}

	/* */
	public void setframe()
	{
		for(int i=0;i<vn;i++)
			xn[i]=i+1;
		setSize(vn*30+250,vn*30+80);
		add(p=new Panel(),"West");
		p.setLayout(new GridLayout(vn+1,1));
		p.add(new Label(""));
		for(int i=0;i<vn;i++)
			p.add(new Label("Variable"+(i+1)));
		add(p0=new Panel(),"Center");
		p0.setLayout(new GridLayout(vn+1,vn));
		for(int i=0;i<vn-1;i++)
			p0.add(new Label("X["+(i+1)+"]"));
		p0.add(new Label(" Y  "));
		for(int i=0;i<vn;i++) 
			for(int j=0;j<vn;j++)
				if(i==j)p0.add(cb[i][j]=new Checkbox("",cg[i],true));
				else p0.add(cb[i][j]=new Checkbox("",cg[i],false));
		add(p1=new Panel(),"East");
		p1.setLayout(new GridLayout(6,1));
		p1.add(cbset[0]=new Checkbox("Loacation in Control space",set[0]));
		p1.add(cbset[1]=new Checkbox("Error histogram (Delay)",set[1]));
		p1.add(cbset[2]=new Checkbox("Error histogram (Maxwell)",set[2]));
		p1.add(cbset[3]=new Checkbox("Effect of variable",set[3]));
		p1.add(cbset[4]=new Checkbox("Probability Density",set[4]));
		p1.add(cbset[5]=new Checkbox("Lock",set[5]));
		add(p2=new Panel(),"South");
		p2.add(b1=new Button("OK"));
		b1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				/*　出力設定の決定　*/
				for(int i=0;i<6;i++)
					if(cbset[i].getState())set[i]=true;
					else set[i]=false;
				/*　入力設定の決定　*/
				for(int i=0;i<vn;i++)
					xn[i]=0;
				for(int i=0;i<vn;i++)
					for(int j=0;j<vn;j++)
						if(cb[i][j].getState())xn[i]=j+1;
				dispose();
			}
		}
			);
		p2.add(b2=new Button("Cancel"));
		b2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				dispose();
			}
		}
			);
	}
}

	/*　データ入力　*/
class getData extends Frame
{
	private int PV=8,PC=400;	/*　変数の数,サンプルの数の最大値　*/
	private TextField[][] Data=new TextField[PV+1][PC];
	private TextField fname,input,rex;
	private Button bt1,bt2,bt3,bt4,bt5;
	private setFrame w2;
	private Panel pg1,pg2,pg3;
	public int cn=30;	/*　デフォルトのサンプル数　*/
	public int un;		/*　解析で使う変数の数　*/
	public int vn=3;	/*デフォルトの変数の数*/
	public String fn="data.txt";	/*　デフォルトのファイル名　*/
	public double[][] data=new double[PV+1][PC];
	public double[][] x=new double[PV][PC];
	public double[] y=new double[PC];
	public getData()
	{
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
		setTitle(fn);
		w2=new setFrame();
		w2.vn=vn;
		w2.setframe();
		add(pg1=new Panel(),"North");
		add(pg2=new Panel(),"Center");
		setSize(vn*100,cn*20+85);
		pg2.setSize(vn*100,cn*20+25);
		add(pg3=new Panel(),"South");
		pg1.add(fname=new TextField(""+fn));
		pg1.add(rex=new TextField(""+cn));
		pg1.add(input=new TextField(""+vn));
		pg2.setLayout(new GridLayout(cn+1,vn));
		for(int j=0;j<vn;j++)
			pg2.add(new Label("Variable"+(j+1)));
		for(int i=0;i<cn;i++)
			for(int j=0;j<vn;j++)
				pg2.add(Data[j][i]=new TextField("",20));
		pg3.add(bt1=new Button("Setting"));
		bt1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				w2.show();
			}
		}
			);
		/*　データの初期化　*/
		pg3.add(bt2=new Button("New"));
		bt2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String s=fname.getText();
				cn=Integer.parseInt(rex.getText());
				vn=Integer.parseInt(input.getText());
				remove(pg2);
				add(pg2=new Panel(),"Center");
				setSize(vn*100,cn*20+85);
				pg2.setSize(vn*100,cn*20+25);
				pg2.setLayout(new GridLayout(cn+1,vn));
				for(int j=0;j<vn;j++)
					pg2.add(new Label("Variable"+(j+1)));
				for(int i=0;i<cn;i++)
					for(int j=0;j<vn;j++)
						pg2.add(Data[j][i]=new TextField("",20));
				w2=new setFrame();
				w2.vn=vn;
				w2.setframe();
			}
		}
			);
		/*　データの読み込み　*/
		pg3.add(bt3=new Button("Read"));
		bt3.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				fn=fname.getText();
				vn=Integer.parseInt(input.getText());
				FileRead(fn);
				setTitle(fn);
				remove(pg2);
				add(pg2=new Panel(),"Center");
				setSize(vn*100,cn*20+85);
				pg2.setSize(vn*100,cn*20+25);
				pg2.setLayout(new GridLayout(cn+1,vn));
				for(int j=0;j<vn;j++)
					pg2.add(new Label("Variable"+(j+1)));
				for(int i=0;i<cn;i++)
					for(int j=0;j<vn;j++)
						pg2.add(Data[j][i]=new TextField(""+data[j][i],20));
				remove(pg1);
				add(pg1=new Panel(),"North");
				pg1.add(fname=new TextField(""+fn));
				pg1.add(rex=new TextField(""+cn));
				pg1.add(input=new TextField(""+vn));
				w2=new setFrame();
				w2.vn=vn;
				w2.setframe();
			}
		}
			);
		/*　解析の開始　*/
		pg3.add(bt5=new Button("Start"));
		bt5.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				int i,j;
				double[] a=new double[PV+1];
				double[] b=new double[PV+1];
				double[] c=new double[PV+1];
				double d;
				double[][] DATA=new double[PV+1][PC];
				double av[]=new double[PV+1];
				double sd[]=new double[PV+1];
				double[] Delay=new double[cn];
				double[] Maxwell=new double[cn];
				int k=0;
				/*　従属変数の取り込み　*/
				for(i=0;i<vn-1;i++)
					if(w2.xn[i]!=0)
					{
						k++;
						for(j=0;j<cn;j++)
							x[k-1][j]=Double.parseDouble(Data[w2.xn[i]-1][j].getText());
					}
				un=k+1;
				/*　独立変数の取り込み　*/
				if(w2.xn[vn-1]!=0)
					for(j=0;j<cn;j++)
						y[j]=Double.parseDouble(Data[w2.xn[vn-1]-1][j].getText());
				for(j=0;j<un+1;j++)
				{
					av[j]=0;
					sd[j]=0;
				}
				for(i=0;i<cn;i++)
				{
					for(j=0;j<un-1;j++)
						DATA[j][i]=x[j][i];
					DATA[un-1][i]=y[i];
				}
				/*　平均と標準偏差の計算　*/
				for(i=0;i<cn;i++)
					for(j=0;j<un;j++)
					{
						av[j]=av[j]+DATA[j][i];
						sd[j]=sd[j]+DATA[j][i]*DATA[j][i];
					}
				for(i=0;i<un;i++)
				{
					av[i] = av[i]/cn;
					sd[i] = Math.sqrt( sd[i]/cn - av[i]*av[i] );
				}
				CuspSurfaceAnalysis cu=new CuspSurfaceAnalysis();
				for(i=0;i<cn;i++)
					for(j=0;j<un;j++)
						cu.data[i][j]=DATA[j][i];
				for(i=0;i<un;i++)
				{
					cu.av[i] = av[i];
					cu.sd[i] = sd[i];
				}
				cu.NV=un;
				cu.N=cn;
				cu.FN=cn;
				cu.V=un-1;
				cu.V1=un;
				cu.V2=2*un;
				cu.V3=3*un;
				cu.W=4+3*(un-1);
				cu.Regression();
				/*　NR法　*/
				cu.okay=cu.MLcusp();
				cu.MLstats(cu.okay);
				cu.show();		
				for(i=0;i<un;i++)
				{
					a[i]=cu.A[i];
					b[i]=cu.B[i];
					c[i]=cu.C[i];
				}
				d=cu.D;
				if(cu.okay || !w2.set[5])
				{
					Predict pre=new Predict();
					pre.N=cn;
					pre.NV=un-1;
					pre.Predict(DATA,av,sd,a,b,c,d);
					/*　推定の表示　*/
					pre.show();
					Location loc=new Location();
					loc.location(d,pre.fa,pre.fb,cn);
					if(w2.set[0])
						loc.show();
					for(i=0;i<cn;i++)
					{
						Delay[i] = pre.y1[i];
						if (pre.NR[i]==3 && DATA[un-1][i]>pre.y2[i]) Delay[i]=pre.y3[i];
						Maxwell[i] = pre.y1[i];
						if (pre.NR[i]==3 && pre.fa[i]>0.0) Maxwell[i]=pre.y3[i];
					}
					histgram h1=new histgram();
					h1.setTitle("Delay error histgram");
					h1.hist(DATA[un-1],Delay,sd[un-1],cn);
					if(w2.set[1])
						h1.show();
					histgram h2=new histgram();
					h2.setTitle("Maxwell error histgram");
					h2.hist(DATA[un-1],Maxwell,sd[un-1],cn);
					if(w2.set[2])
						h2.show();
					effect ef=new effect();
					ef.NV=un-1;
					for(i=0;i<un;i++)
					{
						ef.a[i]=a[i];
						ef.b[i]=b[i];
						ef.c[i]=c[i];
						ef.av[i]=av[i];
						ef.sd[i]=sd[i];
					}
					for(i=0;i<un-1;i++)
						ef.x[i]=av[i];
					ef.d=d;
					ef.Effect();
					if(w2.set[3])
						ef.show();
					PDF pdf=new PDF();
					pdf.NV=un-1;
					for(i=0;i<un;i++)
					{
						pdf.a[i]=a[i];
						pdf.b[i]=b[i];
						pdf.c[i]=c[i];
						pdf.av[i]=av[i];
						pdf.sd[i]=sd[i];
					}
					for(i=0;i<un-1;i++)
						pdf.x[i]=av[i];
					pdf.d=d;
					pdf.PD();
					if(w2.set[4])
						pdf.show();
				}
			}
		}
			);
	}
	/*　ファイル読み込み　*/
	public void FileRead(String s)
	{
		try
		{
			BufferedReader fin=new BufferedReader(new FileReader(s));
			String st;
			int i=0;
			int j=0;
			while((st=fin.readLine())!=null)
			{
				StringTokenizer t1=new StringTokenizer(st,"\t");
				while(t1.countTokens()>0)
				{
					data[j][i]=Double.parseDouble(t1.nextToken());
                    j++;
					if(j>vn-1)
					{
						i++;
						j=0;
					}
				}
			}
			cn=i;
			fin.close();
		}
		catch(IOException e)
		{
			System.err.println("error1");
			System.exit(1);
		}
	}
	public void FileWrite(String s)
	{
		try
		{
			PrintWriter fout=new PrintWriter(new FileWriter(s));
			for(int i=0;i<cn;i++)
			{
				for(int j=0;j<vn;j++)
					fout.print(""+Data[j][i].getText()+"\t");
				fout.println("");
			}
			fout.close();
		}
		catch(IOException e)
		{
			System.err.println("error1");
			System.exit(1);
		}
	}
}

	/*　プログラム本体Applet　*/
public class scta4j extends Applet
{
	private Panel pg1,pg2,pg3;
	private TextField fname,input,used;
	private Button bt1,bt2;
	private getData gd;
	public void init()
	{
		pg1=new Panel();
		pg1.add(new Label("Stocastic Catastrophe Theory Analysis for Java"));
		add(pg1,"North");
		pg2=new Panel();
		add(pg2,"Center");
		pg2.add(bt1=new Button("Start"));
		bt1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				gd=new getData();
				gd.show();
			}
		}
			);
	}
}

	/*　推定　*/
class Predict extends Frame
{
	public int N;
	public int NV;
	public int[] NR;
	private double[][] dat;
	public double[] y1;
	public double[] y2;
	public double[] y3;
	public double[] fa;
	public double[] fb;
	public double[] fl;
	public Predict()
	{
		setTitle("Predict");
		addWindowListener(new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
	}
	public void Predict(double[][] data,double[] av,double[] sd,double[] a,double[] b,double[] c,double d)
	{ 
		int i,j;
		CuspSurfaceAnalysis w=new CuspSurfaceAnalysis();
		fa=new double[N];
		fb=new double[N];
		fl=new double[N];
		y1=new double[N];
		y2=new double[N];
		y3=new double[N];
		NR=new int[N];
		dat=new double[NV+1][N];
		for(i=0;i<N;i++)
		{
			for(j=0;j<NV+1;j++)
				dat[j][i]=data[j][i];
		}
		for(i=0;i<N;i++)
		{
			fa[i]=a[NV];
			fb[i]=b[NV];
			fl[i]=c[NV];
			for(j=0;j<NV;j++)
			{
				fa[i]=fa[i]+a[j]*(data[j][i]-av[j])/sd[j];
				fb[i]=fb[i]+b[j]*(data[j][i]-av[j])/sd[j];
				fl[i]=fl[i]+c[j]*(data[j][i]-av[j])/sd[j];
			}
			w.Cubic( fa[i],fb[i],0.0,-d);
			y1[i] = av[NV] + sd[NV]*(fl[i]+w.R1);
			y2[i] = av[NV] + sd[NV]*(fl[i]+w.R2);
			y3[i] = av[NV] + sd[NV]*(fl[i]+w.R3);
			NR[i] = w.NR;
		}
	}
	public void paint(Graphics g)
	{
		setSize(450+50*(NV+1),100+(N+1)*15);
		g.drawString("Case",50,65);
		g.drawLine(90,50,90,65+N*15);
		g.drawString("Asymm",100,65);
		g.drawString("Bifur",150,65);
		g.drawString("Liner",200,65);
		g.drawLine(240,50,240,65+N*15);
		g.drawString("Mode",250,65);
		g.drawString("Antimode",290,65);
		g.drawString("Mode",350,65);
		g.drawLine(390,50,390,65+N*15);
		g.drawString("Y",400,65);
		for(int i=0;i<NV;i++)
			g.drawString("X["+(i+1)+"]",450+i*50,65);
		for(int i=0;i<N;i++)
		{
			g.drawString(""+(i+1),50,80+15*i);
			g.drawString(""+(double)((int)(fa[i]*1000))/1000,100,80+15*i);
			g.drawString(""+(double)((int)(fb[i]*1000))/1000,150,80+15*i);
			g.drawString(""+(double)((int)(fl[i]*1000))/1000,200,80+15*i);
			g.drawString(""+(double)((int)(y1[i]*1000))/1000,250,80+15*i);
			if(NR[i]!=1)
			{
				g.drawString(""+(double)((int)(y2[i]*1000))/1000,300,80+15*i);
				g.drawString(""+(double)((int)(y3[i]*1000))/1000,350,80+15*i);
			}
			g.drawString(""+dat[NV][i],400,80+15*i);
			for(int j=0;j<NV;j++)
				g.drawString(""+dat[j][i],450+j*50,80+15*i);
		}
	}
}

	/*　コントロール平面におけるデータの位置　*/
class Location extends Frame
{
	private boolean[][] bm=new boolean[400][400];
	private boolean[][] lo=new boolean[400][400];
	private double famax=5,fbmax=5,famin=-5,fbmin=-5;
	private int num=0,all=0;
	public Location()
	{
		setTitle("Location of data in control spase");
		setSize(500,520);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
	}
	public void location(double fd,double[] fa,double[] fb,int N)
	{
		all=N;
		for(int i=0;i<N;i++)
		{
			if(fa[i]<famin)famin=fa[i];
			if(fb[i]<fbmin)fbmin=fb[i];
			if(fa[i]>famax)famax=fa[i];
			if(fb[i]>fbmax)fbmax=fb[i];
		}
		CuspSurfaceAnalysis cu=new CuspSurfaceAnalysis();
		for(int i=0;i<400;i++)
		{
			for(int j=0;j<400;j++)
			{
				if(famin>=-5 || fbmin>=-5 || famax<=5 || fbmax<=5)
					cu.Cubic((double)i/40-5,(double)j/40-5,0.0,-fd);
				else if((famin<-5 && famin>=-10) || (fbmin<-5 && fbmin>=-10) || (famax>5 && famax<=10) || (fbmax>5 && fbmax<=10))
					cu.Cubic((double)i/20-10,(double)j/20-10,0.0,-fd);
				else
					cu.Cubic((double)i/10-20,(double)j/10-20,0.0,-fd);
				if(cu.NR==3)bm[i][j]=true;
			}
		}
		for(int i=0;i<N;i++)
		{
			if(famin>=-5 || fbmin>=-5 || famax<=5 || fbmax<=5)
			{
				int a=(int)((fa[i]+5)*40);
				int b=(int)((fb[i]+5)*40);
				lo[a][b]=true;
				if(bm[a][b])num++;
			}
			else if((famin<-5 && famin>=-10) || (fbmin<-5 && fbmin>=-10) || (famax>5 && famax<=10) || (fbmax>5 && fbmax<=10))
			{
				int a=(int)((fa[i]+10)*20);
				int b=(int)((fb[i]+10)*20);
				lo[a][b]=true;
				if(bm[a][b])num++;
			}
			else
			{
				int a=(int)((fa[i]+20)*10);
				if(a<=0)a=0;
				if(a>=400)a=399;
				int b=(int)((fb[i]+20)*10);
				if(b<=0)b=0;
				if(b>=400)b=399;
				lo[a][b]=true;
				if(bm[a][b])num++;
			}
		}
	}
	public void paint(Graphics g)
	{
		for(int i=0;i<400;i++)
		{
			for(int j=0;j<400;j++)
			{
				g.setColor(Color.lightGray);
				if(bm[i][j])g.drawRect(i+50,400-j+50,1,1);
			}
		}
		g.setColor(Color.black);
		g.drawLine(50,250,450,250);
		g.drawString("A[X]",460,260);
		g.drawLine(250,50,250,450);
		g.drawString("B[X]",240,40);
		if(famin>=-5 || fbmin>=-5 || famax<=5 || fbmax<=5)
			for(int i=0;i<11;i++)
			{
				g.drawString(""+(i-5),40+i*40,260);
				g.drawString(""+(5-i),240,60+i*40);
			}
		else if((famin<-5 && famin>=-10) || (fbmin<-5 && fbmin>=-10) || (famax>5 && famax<=10) || (fbmax>5 && fbmax<=10))
			for(int i=0;i<21;i++)
			{
				g.drawString(""+(i-10),40+i*20,260);
				g.drawString(""+(10-i),240,60+i*20);
			}
		else
			for(int i=0;i<21;i++)
			{
				g.drawString(""+((i-10)*2),40+i*20,260);
				g.drawString(""+((10-i)*2),240,60+i*20);
			}
		for(int i=0;i<400;i++)
		{
			for(int j=0;j<400;j++)
			{
				g.setColor(Color.red);
				if(lo[i][j])g.drawRect(i+50,400-j+50,1,1);
			}
		}
		g.drawString("Fraction of cases in bimodal zone:"+(double)((int)((double)num/(double)all*100000))/1000+"%",50,480);
	}
}

	/*　誤差ヒストグラム　*/
class histgram extends Frame
{
	private double erm=0,erv=0,rsq,ersd;
	private int i;
	private int maxhg=0;
	private int[] hg=new int[23];
	public histgram()
	{
		setSize(400,450);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
	}
	public void hist(double[] x,double[] y,double xsd,int N)
	{
		for(i=0;i<N;i++)
		{
			erm=erm+(x[i]-y[i]);
			erv=erv+(x[i]-y[i])*(x[i]-y[i]);
			int j=(int)((x[i]-y[i])/xsd*2+11);
			if(j<0)j=0;
			if(j>22)j=22;
			hg[j]++;
		}
		for(i=0;i<23;i++)
			if(hg[i]>maxhg)maxhg=hg[i];
		erm=erm/N;
		erv=erv/N-erm*erm;
		rsq=1-erv/(xsd*xsd);
		rsq=(double)((int)(rsq*1000))/1000;
		erm=erm/xsd;
		ersd=Math.sqrt(erv)/xsd;
		erm=(double)((int)(erm*1000))/1000;
		ersd=(double)((int)(ersd*1000))/1000;
	}
	public void paint(Graphics g)
	{
		g.drawLine(50,350,380,350);
		g.drawLine(50,350,50,50);
		if(maxhg<20)
		{
			for(i=0;i<23;i++)
				g.drawRect(50+i*15,350-hg[i]*15,15,hg[i]*15);
			for(i=0;i<4;i++)
				g.drawString(""+i*5,30,350-i*75+8);
		}
		if(maxhg>=20 && maxhg<60)
		{
			for(i=0;i<23;i++)
				g.drawRect(50+i*15,350-hg[i]*5,15,hg[i]*5);
			for(i=0;i<4;i++)
				g.drawString(""+i*15,30,350-i*75+8);
		}
		if(maxhg>=60 && maxhg<100)
		{
			for(i=0;i<23;i++)
				g.drawRect(50+i*15,350-hg[i]*3,15,hg[i]*3);
			for(i=0;i<4;i++)
				g.drawString(""+i*25,30,350-i*75+8);
		}
		if(maxhg>=100)
		{
			for(i=0;i<23;i++)
				g.drawRect(50+i*15,350-hg[i],15,hg[i]);
			for(i=0;i<4;i++)
				g.drawString(""+i*75,30,350-i*75+8);
		}
		for(i=0;i<11;i++)
			g.drawString(""+(i-5),65+i*30-8,366);
		g.drawString("R^2="+rsq,30,390);
		g.drawString("Error Means="+erm,30,410);
		g.drawString("Error St.Dev="+ersd,200,410);
	}
}

	/*　従属変数における独立変数の影響　*/
class effect extends Frame
{
	public int PV=8;
	public double[] a=new double[PV+1];
	public double[] b=new double[PV+1];
	public double[] c=new double[PV+1];;
	public double d;
	public double[] av=new double[PV+1];
	public double[] sd=new double[PV+1];
	public double[] x=new double[PV];
	private Panel pg,p1,p2;
	private Checkbox[] cb=new Checkbox[PV];
	private TextField[] tf;
	public int i=1,NV;
	public effect()
	{
		setSize(650,600);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
	}
	public void Effect()
	{
		Button bt0,bt1;
		add(p1=new Panel(),"North");
		add(p2=new Panel(),"East");
		p1.add(new Label("Effect Variable"));
		CheckboxGroup cg=new CheckboxGroup();
		p1.add(cb[0]=new Checkbox(""+1,cg,true));
		for(int j=1;j<NV;j++)
			p1.add(cb[j]=new Checkbox(""+(j+1),cg,false));
		for(int j=0;j<NV;j++)
			cb[j].addItemListener(new MyListener());
		p1.add(bt1=new Button("OK"));
		p2.setLayout(new GridLayout(25,2));
		tf=new TextField[NV];
		for(int j=0;j<NV;j++)
		{
			if((j+1)!=i)
			{
				p2.add(new Label("X["+(j+1)+"]="));
				p2.add(tf[j]=new TextField(""+(double)((int)(x[j]*1000))/1000,5));
			}
		}
		for(int j=0;j<26-NV;j++)
		{
			p2.add(new Label(""));
			p2.add(new Label(""));
		}
		add(pg=new Panel());
		pg.setSize(500,500);
		bt1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(i>0 && i<=NV)
				{
					setTitle("Effect of X["+i+"] on Y");
					Graphics g=pg.getGraphics();
					Color col=Color.blue;
					int px,py,LPX=50,LPY=450,N=1,s=400,t=400;
					double a0,a1,b0,b1,c0,c1,y,y1,y2,y3;
					double fa,fb,fc,fd;
					double[] R1=new double[400];
					double[] R2=new double[400];
					double[] R3=new double[400];
					int[] NR=new int[400];
					int k,j;
					a1=a[i-1];
					b1=b[i-1];
					c1=c[i-1];
					a0=a[NV];
					b0=b[NV];
					c0=c[NV];
					for(k=0;k<NV;k++)
						if(k!=(i-1))
						{
							x[k]=Double.parseDouble(tf[k].getText());
							a0=a0+a[k]*((x[k]-av[k])/sd[k]);
							b0=b0+b[k]*((x[k]-av[k])/sd[k]);
							c0=c0+c[k]*((x[k]-av[k])/sd[k]);
						}
					CuspSurfaceAnalysis cu=new CuspSurfaceAnalysis();
					for(j=0;j<400;j++)
					{
						y=(double)j*0.025-5;
						fa=a0+a1*y;
						fb=b0+b1*y;
						fc=c0+c1*y;
						fd=d;
						if(fd!=0.0)
						{
							cu.Cubic( fa,fb,0.0,-fd );
							R1[j]=cu.R1;
							R2[j]=cu.R2;
							R3[j]=cu.R3;
							NR[j]=cu.NR;
						}
						else
						{
							NR[j]=1;
							R1[j]=-(a0+a1*y)/(b0+b1*y)+(c0+c1*y);
						}
						if(N==1 && cu.NR==3)s=j;
						if(N==3 && cu.NR==1)t=j;
						N=cu.NR;
					}
					for(j=0;j<s;j++)
					{
						if((b0+b1*((double)j*0.025-5))<(3.0*d*R1[j]*R1[j]))col=Color.blue;
						else col=Color.red;
						px=(int)(50+j);
						py=(int)(250-(R1[j]+(c0+c1*((double)j*0.025-5)))*40);
						if(py<50)py=50;
						if(py>450)py=450;
						if(j!=0)
						{
							g.drawLine(LPX,LPY,px,py);
							g.setColor(col);
						}
						LPX=px;
						LPY=py;
					}
					for(j=s;j<t;j++)
					{
						if((b0+b1*((double)j*0.025-5))<(3.0*d*R1[j]*R1[j]))col=Color.blue;
						else col=Color.red;
						px=(int)(50+j);
						py=(int)(250-(R1[j]+(c0+c1*((double)j*0.025-5)))*40);
						if(py<50)py=50;
						if(py>450)py=450;
						if(j!=s)
						{
							g.drawLine(LPX,LPY,px,py);
							g.setColor(col);
						}
						LPX=px;
						LPY=py;
					}
					for(j=t-1;j>=s;j--)
					{
						if((b0+b1*((double)j*0.025-5))<(3.0*d*R2[j]*R2[j]))col=Color.blue;
						else col=Color.red;
						px=(int)(50+j);
						py=(int)(250-(R2[j]+(c0+c1*((double)j*0.025-5)))*40);
						if(py<50)py=50;
						if(py>450)py=450;
						if(j!=t-1)
						{
							g.drawLine(LPX,LPY,px,py);
							g.setColor(col);
						}
						LPX=px;
						LPY=py;
					}
					for(j=s;j<t;j++)
					{
						if((b0+b1*((double)j*0.025-5))<(3.0*d*R3[j]*R3[j]))col=Color.blue;
						else col=Color.red;
						px=(int)(50+j);
						py=(int)(250-(R3[j]+(c0+c1*((double)j*0.025-5)))*40);
						if(py<50)py=50;
						if(py>450)py=450;
						if(j!=s)
						{
							g.drawLine(LPX,LPY,px,py);
							g.setColor(col);
						}
						LPX=px;
						LPY=py;
					}
					for(j=t;j<400;j++)
					{
						if((b0+b1*((double)j*0.025-5))<(3.0*d*R1[j]*R1[j]))col=Color.blue;
						else col=Color.red;
						px=(int)(50+j);
						py=(int)(250-(R1[j]+(c0+c1*((double)j*0.025-5)))*40);
						if(py<50)py=50;
						if(py>450)py=450;
						if(j!=t)
						{
							g.setColor(col);
							g.drawLine(LPX,LPY,px,py);
						}
						LPX=px;
						LPY=py;
					}
					g.setColor(Color.white);
					g.drawLine(50,50,450,50);
					g.setColor(Color.black);
					g.drawLine(50,50,50,450);
					g.drawLine(50,450,450,450);
					for(j=0;j<9;j++)
					{
						y=(double)(int)(((j-4)*sd[i-1]+av[i-1])*100)/100;
						g.drawString(""+y,75+j*40,470);
					}
					g.drawString("X["+i+"]",450,470);
					for(j=0;j<9;j++)
					{
						y=(double)(int)(((j-4)*sd[NV]+av[NV])*100)/100;
						g.drawString(""+y,20,416-j*40);
					}
					g.drawString("Y",20,50);
					g.dispose();
				}
			}
		}
			);
		Button bt2;
		add(bt2=new Button("消去"),"South");
		bt2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pg.repaint();
			}
		}
			);
	}
     
	class MyListener implements ItemListener
	{
		public void itemStateChanged(ItemEvent e)
		{
			for(int j=0;j<NV;j++)
				if(e.getSource()==cb[j])
					i=j+1;
			remove(p2);
			add(p2=new Panel(),"East");
			p2.setLayout(new GridLayout(25,2));
			tf=new TextField[NV];
			for(int j=0;j<NV;j++)
			{
				if((j+1)!=i)
				{
					p2.add(new Label("X["+(j+1)+"]="));
					p2.add(tf[j]=new TextField(""+(double)((int)(x[j]*1000))/1000,5));
				}
			}
			for(int j=0;j<26-NV;j++)
			{
				p2.add(new Label(""));
				p2.add(new Label(""));
			}
		}
	}
}

	/*　確率密度関数の影響　*/
class PDF extends Frame
{
	public int NV;
	public int PV=8;
	public double[] a=new double[PV+1];
	public double[] b=new double[PV+1];
	public double[] c=new double[PV+1];;
	public double d;
	public double[] av=new double[PV+1];
	public double[] sd=new double[PV+1];
	public double[] x=new double[PV];
	private Panel pg,p1;
	private TextField[] tf;
	public PDF()
	{
		setTitle("PDF");
		setSize(650,600);
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);
	}
	public void PD()
	{
		Button bt1,bt2;
		add(p1=new Panel(),"East");
		p1.setLayout(new GridLayout(20,2));
		tf=new TextField[NV];
		for(int j=0;j<NV;j++)
		{
			p1.add(new Label("X["+(j+1)+"]="));
			p1.add(tf[j]=new TextField(""+(double)((int)(x[j]*1000))/1000,5));
		}
		for(int j=0;j<19-NV;j++)
		{
			p1.add(new Label(""));
			p1.add(new Label(""));
		}
		add(pg=new Panel(),"Center");
		pg.setSize(500,500);
		p1.add(bt1=new Button("消去"));
		bt1.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				pg.repaint();
			}
		}
			);
		p1.add(bt2=new Button("OK"));
		bt2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Graphics g=pg.getGraphics();
				Color col=Color.blue;
				int px,py,LPX=50,LPY=450;
				double fc,y;
				double[] f=new double[4];
				double[] fx=new double[400];
				int k,j;
				f[0]=a[NV];
				f[1]=b[NV];
				f[2]=0.0;
				f[3]=d;
				fc=c[0];
				for(k=0;k<NV;k++)
				{
					x[k]=Double.parseDouble(tf[k].getText());
					f[0]=f[0]+a[k]*((x[k]-av[k])/sd[k]);
					f[1]=f[1]+b[k]*((x[k]-av[k])/sd[k]);
					fc=fc+c[k]*((x[k]-av[k])/sd[k]);
				}
				f[0]=-f[0];
				f[1]=-f[1]/2;
				f[2]=-f[2]/3;
				f[3]=f[3]/4;				
				CuspSurfaceAnalysis cu=new CuspSurfaceAnalysis();
				for(j=0;j<400;j++)
				{
					y=(double)j*0.025-5.0-fc;
					cu.Moments(f);
					fx[j]=Math.exp(cu.psi-(f[0]*y+f[1]*y*y+f[3]*y*y*y*y));
				}
				for(j=0;j<400;j++)
				{						
					px=(int)(50+j);
					py=(int)(450-fx[j]*250);
					if(py<50)py=50;
					if(py>450)py=450;
					if(j!=0)
					{
						g.drawLine(LPX,LPY,px,py);
						g.setColor(col);
					}
					LPX=px;
					LPY=py;
				}
				g.setColor(Color.black);
				g.drawLine(50,50,50,450);
				g.drawLine(50,450,450,450);
				for(j=0;j<9;j++)
				{
					y=(double)(int)(((j-4)*sd[NV]+av[NV])*100)/100;
					g.drawString(""+y,75+j*40,470);
				}
				g.drawString("Y",450,470);
				for(j=0;j<=10;j++)
				{
					g.drawString(""+((double)(j)/10),20,450-j*25);
				}
				g.drawString("P[Y]",20,35);
				g.setColor(Color.white);
				g.drawLine(50,50,450,50);
				g.dispose();
			}
		}
			);
	}
}

	/* カスプ面解析 */
class CuspSurfaceAnalysis extends Frame
{
	private int PV=8;
	private int PW=28;
	private int NC=400;
	public double R1,R2,R3;
	public double yr, yi;
	public int NR;
	public double[] F=new double[100];
	public double start, stop, dx;
	private double[] r = new double[4];
	public double psi;
	public double[] tm=new double[8];
	public double[] M=new double[8];
	public boolean okay;
	public boolean numb;
	public int debug;
	public double[][] data = new double[NC][PV];
	public double[] av = new double[PV];
	public double[] sd = new double[PV];
	public double[][] rg=new double[PW][PW];
	public int NV,N;
	public double det;
	public double[] A = new double[PV];
	public double[] B = new double[PV];
	public double[] C = new double[PV];
	public double D;
	public int V, V1, V2, V3, W, df;
	public int FN;
	private double L1;
	private double L2;
	private double pi=3.14159;
	private double[] RA=new double[PV];
	private double[] RB=new double[PV];
	private double[] RC=new double[PV];
	private double RD;
	private double[] TA=new double[PV];
	private double[] TB=new double[PV];
	private double[] TC=new double[PV];
	private double[] se=new double[PW];
	private double CH;
	private double TD;
	private int DF1;
	private int DF2;
	private double[] R = new double[PV];
	private double[] DEG = new double[PV];
	private double[] LEN = new double[PV];
	private double Ysd, Yav;
	private double Rsq;
	private int NL;
	private int[] px = new int[PV];
	private int st[] = new int[70];
	private int pt[][] = new int[21][21];

	public CuspSurfaceAnalysis()
	{
		setTitle("Cusp Surface Analysis");
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		}
			);		
	}
     
	/*　べき乗計算　*/
	public double XpwrY(double x,double y)
	{
		double XpwrY=Math.exp( y*Math.log(x) );
		return XpwrY;
	}
    
	/*　カルダノの解法による3次方程式の解の推定　*/
	public void Cubic(double fa,double fb,double fc,double fd)
	{
		int k;
		double ttpi,third,disc,TR;
		double f,g,h,p,q,u,w;

		f=(-27*fa*fd*fd+9*fb*fc*fd-2*fc*fc*fc)/(54*fd*fd*fd);
		g=(-3*fb*fd+fc*fc)/(9*fd*fd);
		h=-fc/(3*fd);
      
		third=1.0/3;
		disc=f*f-g*g*g;

		if(Math.abs(disc)< 1e-10)disc=0;
		if(disc>0)
		{
			NR=1;
			w=f+Math.sqrt(disc);
			if(w<0) p=-XpwrY(-w,third);
			else p=XpwrY( w,third);
			w=f-Math.sqrt(disc);
			if(w<0) q=-XpwrY(-w,third);
			else q=XpwrY( w,third);
			R1=h+p+q;
			R2=h-(p+q)/2;
			R3=(p-q)*Math.sqrt(3)/2;
		}
		else
		{
			NR=3;
			ttpi=2*third*pi;
			if(disc==0)
				if(f<0) w=third*pi;
				else w=0;
			else
			{
				u=f/(g*Math.sqrt(g));
				q=Math.sqrt(1-u*u);
				if(q<1e-10)
					if(u<0) w=third*pi;
					else w=0;
				else w=third*(0.5*pi-Math.atan(u/q));
			}
			p=2*Math.sqrt(g);
			R1=h+p*Math.cos(w);
			R2=h+p*Math.cos(w + ttpi );
			R3=h+p*Math.cos(w - ttpi );

			for(k=1;k<=2;k++)
			{
				if(R1>R2)
				{
					TR=R2;
					R2=R1;
					R1=TR;
				}
				if(R2>R3)
				{
					TR=R3;
					R3=R2;
					R2=TR;
				}
			}
		}
	}

	public void Csqrt( double xr,double xi)
	{
		double a, m;
		if(xi == 0)
		{
			if(xr < 0)
			{
				yr = 0;
				yi = Math.sqrt(-xr);
			}
			else
			{
				yr = Math.sqrt(xr);
				yi = 0;
			}
		}
		else
		{
			if(xr == 0)
			{
				m  = 0.5*Math.sqrt(2*Math.abs(xi));
				yr = m;
				if(xi > 0) yi =  m;
				else yi = -m;
			}
			else
			{
				m = Math.sqrt( Math.sqrt( xr*xr + xi*xi ) );
				a = 0.5*Math.atan( xi/xr );
				if(a < 0)a = a + pi;
				yr = m*Math.cos( a );
				yi = m*Math.sin( a );
			}
		}
	}

	public void Quartic( double fa,double fb,double fc,double fd,double fe)
	{
		double f = 0.25;
		double h = 0.5;
		int i, j, k;
		double at, bt, p, q, s, u, v, y;
		double temp, re, im;
		fa = fa/fe;
		fb = fb/fe;
		fc = fc/fe;
		fd = fd/fe;

		at = 4*fa*fc - fb*fb - fa*fd*fd;
		bt = fb*fd - 4*fa;
		Cubic( at, bt, -fc, 1.0);
		y = R1;
		N = 0;
		for(i=1;i<=2;i++)
		{
			if(i==1) s = 1.0;
			else s = -1.0;
			Csqrt( f*y*y-fa, 0.0);
			p=yr;
			q=yi;
			p = h*y + s*p;
			q = s*q;
			Csqrt( f*fd*fd+y-fc, 0.0);
			u=yr;
			v=yi;
			u = h*fd + s*u;
			v = s*v;
			Csqrt( f*(u*u-v*v)-p, h*u*v-q);
			re=yr;
			im=yi;
			if(Math.abs(im+h*v) < 1e-5)
			{
				N = N + 1;
				r[N-1] = -re - h*u;
			}
			if(Math.abs(im-h*v) < 1e-5)
			{
				N = N + 1;
				r[N-1] = re - h*u;
			}
		}
		if(N>1)
		{
			for(k = 1; k<=N-1; k++)
				for(i = k; i<= N-1; i++)
				{
					j = i - 1;
					if(r[j]>r[i])
					{
						temp = r[j];
						r[j] = r[i];
						r[i] = temp;
					}
				}
		}
	}

	public boolean CDF(double[] coef,int pts)
	{
		int i, N;
		double A, B, C, D;
		double Q1, Q3, Qmax, con;
		double x, sum;
		boolean Okay;

		Okay = false;
		A = coef[0];
		B = coef[1];
		C = coef[2];
		D = coef[3];
		if(D <= 0.0 )
			System.out.println("CDF received bad coefficients.");
		else
		{
			Cubic( A, 2.0*B, 3.0*C, 4.0*D);
			Q1 = Math.exp( - A*R1 - B*R1*R1 - C*R1*R1*R1 - D*R1*R1*R1*R1 );
			Q3 = 0;
			if(NR==3)
				Q3 = Math.exp( - A*R3 - B*R3*R3 - C*R3*R3*R3 - D*R3*R3*R3*R3 );
			if(Q1 > Q3) Qmax = Q1;
			else Qmax = Q3;
			if(Qmax == 0) System.out.println("CDF received a degenerate PDF.");
			else
			{
				con = -Math.log( Qmax );
				N=0;
				Quartic( -120.0,A,B,C,D);
				if(N<2)System.out.println("CDF could not determine }points.");
				else
				{
					Okay = true;
					start = r[0];
					stop  = r[N-1];
					dx = (stop - start)/pts;

					sum = 0;
					x = start - dx/2.0;
					for(i = 0;i<= pts-1;i++)
					{
						x = x + dx;
						F[i] = Math.exp( con - (((D*x + C)*x + B)*x + A)*x );
						sum = sum + F[i];
					}
					F[0] = F[0]/sum;
					for(i = 1;i <= pts-1;i++) F[i] = F[i-1] + F[i]/sum;
				}
			}
		}
		return(Okay);
	}

	public void Trapez(double[] cf)
	{
		int pts = 64;
		int i, j;
		double A, B, C, D;
		double S0, S1, S2;
		double Q1, Q3, Qmax, con;
		double[] R=new double[4];
		double x, f, cd, sum;
		double[] T=new double[3];

		okay = false;
		A = cf[0];
		B = cf[1];
		C = cf[2];
		D = cf[3];
		if(D <= 0 )System.out.println("Trapez receives bad coefficients.");
		else
		{
			Cubic( A, 2.0*B, 3.0*C, 4.0*D);
			if(debug == 3)
				if(NR==1) System.out.println("Unimodal");
				else System.out.println("Bimodal");

			Q1 = Math.exp( - A*R1 - B*R1*R1 - C*R1*R1*R1 - D*R1*R1*R1*R1 );
			Q3 = 0.0;
			if(NR==3) Q3 = Math.exp( - A*R3 - B*R3*R3 - C*R3*R3*R3 - D*R3*R3*R3*R3 );
			if(Q1 > Q3) Qmax = Q1;
			else Qmax = Q3;
			if(Qmax == 0.0) System.out.println("Trapez receives a degenerate PDF.");
			else
			{
				con = -Math.log( Qmax );
				Quartic( -120.0,A,B,C,D);
				if(N<2) System.out.println("Trapez cannot determine }points.");
				else
				{
					okay = true;
					start = r[0];
					stop  = r[N-1];
					dx = (stop - start)/pts;
					S0 = 0.0;
					S1 = 0.0;
					S2 = 0.0;
					x = start - dx/2.0;
					for (i = 0; i<=pts-1; i++)
					{
						x = x + dx;
						f = Math.exp( con - (((D*x + C)*x + B)*x + A)*x );
						S0 = S0 + f;
						S1 = S1 + f*x;
						S2 = S2 + f*x*x;
					}
					M[0] = S1/S0;
					M[1] = S2/S0;
					psi = con - Math.log( S0*dx );
					cd = 4.0*cf[3];
					T[0] = -3.0*cf[2]/cd;
					T[1] = -2.0*cf[1]/cd;
					T[2] = -  cf[0]/cd;
					M[2] = T[0]*M[1] + T[1]*M[0] + T[2];
					for (i = 3;i<=7;i++)
					{
						sum = (i-2)/cd;
						if(i>3) sum = sum*M[i-4];
						for( j=0 ; j<= 2 ; j++)
							sum = sum + T[j]*M[i-j-1];
						M[i] = sum;
					}
				}
			}
		}
	}

	public double Confl(double a,double g,double z)
	{
		double cv, fk, fa, fg, x, y;
		boolean done;

		cv = 1e-10;
		fk = 1.0;
		fa = a;
		fg = g;
		x = (fa/fg)*z;
		y = 1.0;
		done = false;
		y = y + x;
		if(Math.abs(x/y) < cv) done = true;
		else
		{
			fk = fk + 1.0;
			fa = fa + 1.0;
			fg = fg + 1.0;
			x = x *(fa/fg)*(z/fk);
		}
		while(!done)
		{
			y = y + x;
			if(Math.abs(x/y) < cv) done = true;
			else
			{
				fk = fk + 1.0;
				fa = fa + 1.0;
				fg = fg + 1.0;
				x = x *(fa/fg)*(z/fk);
			}
		}
		return(y);
	}

	/*　4次方程式からモーメント法によりPsiを求める　*/
	public void  Moments(double[] c)
	{
		double T1 = 3.1415926535898;
		double T2 = 0.8160489390983;
		double T3 = 0.2758156628302;
		double T4 = 1.5707963267949;
		double T5 = 1.103262651321;
		double Q1 = 0.25;
		double Q2 = 0.5;
		double Q3 = 0.75;
		double Q4 = 1.0;
		double Q5 = 1.25;
		double Q6 = 1.5;
		double A, B, L, D, F, G, S, X, Y, Z;
		double sum0, sum1, sum2, PS, CV, FK, MX, MY, MW, CD, mu, s2;
		double[] H = new double[3];
		int i, j, k;
		boolean notdone, converge;

		notdone = true;
		okay = true;
		numb = false;

		if(c[3] <= 0.0)
		{
			if(c[3] < 0.0) okay = false;
			else
				if(c[2] != 0.0) okay = false;
			else
				if(c[1] <= 0.0) okay = false;
			else
			{
				if(debug == 3)System.out.println("Normal");
				mu = -c[0]/(2.0*c[1]);
				s2 = 1.0/(2.0*c[1]);
				M[0] = mu;
				M[1] = s2 + mu*mu;
				for (k = 1 ;k <= 6; k++)
					M[k+1] = mu*M[k] + (k+1)*s2*M[k-1];
				psi = -0.5*( Math.log(2.0*T1*s2) + mu*mu/s2 );
				notdone = false;
			}
		}

		if(okay && notdone)
		{
			D = 4.0*c[3];
			L = -c[2]/D;
			B = -2.0*c[1] + 3.0*D*L*L;
			A = -c[0] + B*L - D*L*L*L;

			Y = B*B/(4.0*D);

			if(Y<10.0)
			{
				Z = B/Math.sqrt(D);
				X = T1*XpwrY(D,-Q1);
				PS = X*(T2*Confl(Q1,Q2,Y) + T3*Z*Confl(Q3,Q6,Y));
				X = T4*XpwrY(D,-Q3);
				MX = X*(T5*Confl(Q3,Q2,Y) + T2*Z*Confl(Q5,Q6,Y))/PS;
				sum0 = 1.0;
				sum1 = A*MX;
				sum2 = MX;
				converge = true;
				if(A!=0)
				{
					MY = 1.0;
					F = B/D;
					G = 1.0/D;
					X = 0.5*A*A;
					sum0 = 1.0 + X*MX;
					sum1 = A*MX;
					CV = 1e-12;
					FK = 4.0;
					converge = false;
					MW = F*MX + (FK-3.0)*G*MY;
					MY = MX;
					MX = MW;
					converge=(Math.abs(X*MX) < sum2*CV);
					if(!converge)
					{
						sum2 = sum2 + X*MX;
						X = X*A/(FK-1.0);
						sum1 = sum1 + X*MX;
						X = X*A/FK;
						sum0 = sum0 + X*MX;
						FK = FK + 2.0;
					}
					while(!converge && FK<=50.0)
					{
						MW = F*MX + (FK-3)*G*MY;
						MY = MX;
						MX = MW;
						converge=(Math.abs(X*MX) < sum2*CV);
						if(!converge)
						{
							sum2 = sum2 + X*MX;
							X = X*A/(FK-1.0);
							sum1 = sum1 + X*MX;
							X = X*A/FK;
							sum0 = sum0 + X*MX;
							FK = FK + 2.0;
						}
					}
				}
				if(debug == 3)
					if(!converge) System.out.println("NO CONVERGENCE");
				if(converge)
				{
					psi = -A*L + Q2*B*L*L - Q1*D*L*L*L*L - Math.log(PS*sum0);
					M[0] = sum1/sum0 + L;
					M[1] = sum2/sum0 + 2*L*M[0] - L*L;
					CD = D;
					H[0] = -3.0*c[2]/CD;
					H[1] = -2.0*c[1]/CD;
					H[2] = -c[0]/CD;
					M[2] = H[0]*M[1] + H[1]*M[0] + H[2];
					for (i = 3;i <= 7; i++)
					{
						S = (i-2)/CD;
						if(i>3) S = S*M[i-4];
						for (j = 0; j <= 2;j++)
							S = S + H[j]*M[i-j-1];
						M[i] = S;
					}
					notdone = false;
				}
			}

			if(notdone)
			{
				Trapez( c );
				if(okay)numb = true;
			}
		}
	}

	/*　逆行列を求める　*/
	public boolean MatInv(int dim)
	{
		double pivot;
		double max, temp;
		int[] rowindex=new int[PW];
		int[] colindex=new int[PW];
		int h, i, j, k, m, row=0, col=0;
		boolean[] notused=new boolean[PW];
		boolean notdone,Okay;

		Okay = true;
		notdone = true;
		det = 1.0;
		for(i=0;i<=dim-1;i++)notused[i] = true;
		if((dim > PW) || (dim < 1))
		{
			System.out.println("Bad dimension in MatInv:"+dim);
			det = 0.0;
			Okay = false;
		}

		if(dim == 1)
		{
			notdone = false;
			det = rg[0][0];
			if(det == 0) Okay = false;
			else rg[0][0] = 1/det;
		}

		i = 1;
		while(notdone && Okay)
		{
			max = 0;
			for (j = 0; j <= dim-1; j++)
				if(notused[j])
					for(k=0;k<=dim-1;k++)
					{
						temp = Math.abs(rg[j][k]);
						if(notused[k] && (max <= temp))
						{
							row = j;
							col = k;
							max = temp;
						}
					}
			notused[col] = false;
			if(row != col)
			{
				det = -det;
				for(m = 0 ; m <= dim-1; m++)
				{
					temp  = rg[row][m];
					rg[row][m] = rg[col][m];
					rg[col][m] = temp;
				}
			}
			rowindex[i] = row;
			colindex[i] = col;
			pivot = rg[col][col];
			det = det*pivot;
			if(Math.abs(det) > 1e-15)
			{
				rg[col][col] = 1.0;
				temp = 1/pivot;
				for(m = 0; m <= dim-1; m++) rg[col][m] = rg[col][m]*temp;
				for(h = 0; h <= dim-1; h++)
					if(h != col)
					{
						temp = rg[h][col];
						rg[h][col] = 0;
						for(m = 0; m <= dim-1; m++) rg[h][m] = rg[h][m] - temp*rg[col][m];
					}
			}
			else
			{
				System.out.println("");
				System.out.println("**** Inversion trouble! ****");
				Okay =false;
			}
			i = i + 1;
			notdone = (i<=dim);
		}

		if(Okay && (dim>1))
		{
			for(i=dim-1;i>=0;i--)
			{
				row = rowindex[i];
				col = colindex[i];
				if(row != col)
					for (k = 0;k<=dim-1;k++)
					{
						temp = rg[k][row];
						rg[k][row] = rg[k][col];
						rg[k][col] = temp;
					}
			}
		}
		return(Okay);
	}

	/*　相関係数から初期モデルとなる線形回帰モデルを求める　*/
	public void Regression()
	{
		double[] x = new double[PV];
		double det;
		int i, j, k;
		boolean Okay;

		for(i = 0 ; i <= NV-1 ; i++)
			for(j = 0 ; j <= NV-1 ; j++)
				rg[i][j] = 0.0;

		for(k = 0 ; k <= N-1 ; k++)
		{
			for(i = 0 ; i <= NV-1 ; i++)
			{
				x[i] = (data[k][i]-av[i])/sd[i];
				data[k][i] = x[i];
			}
			for (i = 0 ; i <= NV-1 ; i++)
				for(j = 0 ; j <= NV-1 ; j++)
					rg[i][j] = rg[i][j] + x[i]*x[j];
		}

		for(i = 0 ; i <= NV-1 ; i++)
			for(j = 0 ; j <= NV-1 ; j++)
				rg[i][j] = rg[i][j]/FN;

		if(V > 0)
		{
			System.out.println("");
			System.out.println("Correlation matrix:");
			System.out.println("");
			System.out.print("\t");
			for(j=0;j<=NV-1;j++) System.out.print(""+j+"\t");
			System.out.println("");
			for(i = 0;i <= NV-1;i++)
			{
				System.out.print(""+i+"\t");
				for(j=0;j<=NV-1;j++) System.out.print(""+(double)((int)(rg[i][j]*1000))/1000+"\t");
				System.out.println("");
			}
		}

		Okay = MatInv(NV);

		if(debug == 2)
		{
			System.out.println("Inverse of correlation matrix:");
			for(i = 0;i <= NV-1;i++)
			{
				for(j = 0;j <= NV-1;j++) 
					System.out.print(""+(double)((int)(rg[i][j]*1000))/1000);
				System.out.println("");
			}
		}

		if(Okay)
		{
			Rsq = 1.0 - 1.0/rg[NV-1][NV-1];
			L1 = -0.5*FN*(1.0 + Math.log(2*pi*(1.0-Rsq)));
			System.out.println("");
			if(V > 0)
			{
				System.out.println("Log-likelihood of the linear model: "+(double)((int)(L1*10000))/10000);
				System.out.println("");
				System.out.println("Standard linear regression coefficients: ");
				System.out.println("");
				System.out.println("Var Slope");
				for( k = 0; k<= V-1; k++)
				{
					R[k] = -rg[NV-1][k]/rg[NV-1][NV-1];
					System.out.println( px[k] + (double)((int)(R[k]*1000))/1000);
				}
				System.out.println("");
				System.out.println("Multiple R-squared: "+(double)((int)(Rsq*1000))/1000);
			}
			else
			{
				System.out.println("Log-likelihood of the normal density: "+(double)((int)(L1*10000))/10000);
			}

			for (k = 0 ;k<= V-1;k++)
			{
				A[k] = 0.0;
				B[k] = 0.0;
				C[k] = R[k];  
			}
		}
		A[NV-1] = 0.0;
		B[NV-1] = -rg[NV-1][NV-1]; 
		C[NV-1] = 0.0;
		D  = 0.0;
	}
	
	/*　NR法の実行　*/
	public boolean MLcusp()
	{
		int i, j, k;
		int it;
		double[] cf = new double[PW];
		double[] gd = new double[PW];
		double[] cd = new double[PW];
		double[] x = new double[PV];
		double xi, xij, xia, xib, xic, sum;
		double paa, pab, pac, pad, pbb, pbc, pbd, pcc, pcd;
		double cdmax, rate, damp=1.0;
		int NI;
		double ys, yp2, yp3, yp4;
		double Previous;
		double A0, B0, C0, D0;
		double A1, B1, C1, D1;
		double[] cc = new double[4];
		double[] tm = new double[8];
		double[][] cv =new double[4][4];
		boolean done;
		boolean near;

		for(i=0 ; i <= V1-1 ; i++)
		{
			cf[i] = -A[i];
			cf[i+V1] = -B[i]/2;
			cf[i+V2] = -C[i];
		}
		cf[W-1] = D/4;

		it = 1;
		done = false;
		near = false;

		while(!done)
		{
			for(i = 0 ; i<=W-1 ; i++)
			{
				gd[i] = 0.0;
				for(j = 0 ; j <= W-1 ; j++)
					rg[i][j] = 0.0;
			}
			Previous = L2;
			L2 = 0.0;
			NI = 0;

			for (k=0;k<=N-1;k++)
			{
				A0 = cf[V1-1];
				B0 = cf[V2-1];
				C0 = cf[V3-1];
				D0 = cf[W-1];
				if(V > 0)
					for(i = 0 ; i <= V-1 ; i++)
					{
						xi = data[k][i];
						x[i] = xi;
						A0 = A0 + cf[i]*xi;
						B0 = B0 + cf[i+V1]*xi;
						C0 = C0 + cf[i+V2]*xi;
					}
				x[V1-1] = 1.0;
				ys = data[k][V1-1] + C0;
				yp2 = ys*ys;
				yp3 = ys*yp2;
				yp4 = ys*yp3;

				cc[0] = A0;
				cc[1] = B0;
				cc[2] = 0.0;
				cc[3] = D0;

				Moments( cc);
				
				if(numb)
				{
					NI = NI + 1;
					System.out.print( '+' );
				}
				else
				{
					System.out.print( '-' );
				}

				if(okay)
				{
					for(i = 0 ; i <= 3 ; i++)
						for(j = i ; j <= 3 ; j++)
							cv[i][j] = M[i+j+1] - M[i]*M[j];

					L2 = L2 + psi - (A0*ys + B0*yp2 + D0*yp4);

					A1 =A0;
					B1 = 2.0*B0;
					D1 = 4.0*D0;
					for(i = 0 ; i <= V1-1 ; i++)
					{
						xi = x[i];
						xia = xi*( M[0] - ys  );
						xib = xi*( M[1] - yp2 );
						xic = xi*( - A1 - B1*ys - D1*yp3 );
						gd[i] = gd[i] + xia;
						gd[i+V1] = gd[i+V1] + xib;
						gd[i+V2] = gd[i+V2] + xic;
					}
					gd[W-1] = gd[W-1] + M[3] - yp4;

					paa = cv[0][0];
					pab = cv[0][1];
					pad = cv[0][3];
					pbb = cv[1][1];
					pbd = cv[1][3];
					pcc = B1 + 3.0*D1*yp2;
					if(near)
					{
						pac = 1.0;
						pbc = 2.0*ys;
						pcd = 4.0*yp3;
					}
					else
					{
						pac = 0.5;
						pbc = ys;
						pcd = 2.0*yp3;
					}
					for(i = 0 ; i <= V1-1 ; i++)
					{
						xi = x[i];
						for(j = 0 ; j <= V1-1 ; j++)
						{
							xij = xi*x[j];
							rg[i][j] = rg[i][j] + xij*paa;
							rg[i][j+V1] = rg[i][j+V1] + xij*pab;
							rg[i][j+V2] = rg[i][j+V2] + xij*pac;
							rg[i+V1][j+V1] = rg[i+V1][j+V1] + xij*pbb;
							rg[i+V1][j+V2] = rg[i+V1][j+V2] + xij*pbc;
							rg[i+V2][j+V2] = rg[i+V2][j+V2] + xij*pcc;
						}
						rg[i][W-1] = rg[i][W-1] + xi*pad;
						rg[i+V1][W-1] = rg[i+V1][W-1] + xi*pbd;
						rg[i+V2][W-1] = rg[i+V2][W-1] + xi*pcd;
					}
					rg[W-1][W-1] = rg[W-1][W-1] + cv[3][3];
				}
				else
				{
					System.out.print('*' );
				}
			}
			
			System.out.println("");
			System.out.println("Step "+it+",  Log-likelihood:"+(double)((int)(L2*1000))/1000);
			if(debug == 3)
			{
				System.out.println("Coefficients:");
				for( i = 0 ; i<=W-1 ;i++)
					System.out.print((double)((int)(cf[i]*100))/100);
				System.out.println("");
			}

			for (i = 0; i<=W-1; i++)
			{
				gd[i] = gd[i]/FN;
				for (j = i; j<=W-1; j++)
				{
					rg[i][j] = rg[i][j]/FN;
					rg[j][i] = rg[i][j];
				}
			}
			if(debug == 3)
			{
				System.out.println("Gradient:");
				for (i = 0; i<=W-1; i++)
					System.out.print( (double)((int)(gd[i]*1000))/1000 );
				System.out.println("");
				System.out.println("Hessian:");
				for (i = 0; i<=W-1; i++)
				{
					for (j = 0; j<=W-1; j++)
						System.out.print( (double)((int)(rg[i][j]*1000))/1000 );
					System.out.println("");
				}
			}

			okay = MatInv(W);

			if(det<0)
			{
				System.out.println("");
				System.out.println("det(Hessian) is negative: "+det);
				if(near)
				{
					System.out.println("Trying again...");
					near = false;
				}
				else
				{
					okay = false;
				}
			}

			if(det==0)
			{
				System.out.println("");
				System.out.println("det(Hessian) is zero.");
				okay = false;
			}

			if(okay && (L2<Previous) && (it>2))
			{
				System.out.println("");
				System.out.println("Iterations halted: likelihood is not increasing.");
				System.out.println("Current log-likelihood:"+(double)((int)(L2*10000))/10000);
				okay = false;
			}

			if(okay)
			{
				for (i = 0; i<=W-1; i++)
				{
					sum = 0.0;
					for (j = 0; j<=W-1; j++)
						sum = sum + rg[i][j]*gd[j];
					cd[i] = sum;
				}

				damp = 1.0;
				rate = -0.1;
				if(cd[W-1] < rate*cf[W-1])damp = rate*cf[W-1]/cd[W-1];

				if(damp != 1.0)
				{
					System.out.println("");
					System.out.println("Damping factor is :"+(double)((int)(damp*10000))/10000);
				}

				if(damp <= 0)
				{
					System.out.println("");
					System.out.println("Iteration halted: cubic coefficient is vanishing.");
					okay = false;
				}
			}

			if(okay)
			{
				cdmax = 0.0;
				for (i = 0; i<=W-1; i++)
				{
					xi = damp*cd[i];
					cd[i] = xi;
					if(cdmax < Math.abs(xi))cdmax = Math.abs(xi);
				}
				near = (cdmax < 0.1 );
				done = (cdmax < 0.01);
				if(near)
				{
					System.out.println("*** NEAR ***");
				}
				for (i = 0; i<=W-1; i++)
					cf[i] = cf[i] + cd[i];
				if((cf[V2-1] > 20*cf[W-1]) && (it > 5))
				{
					System.out.println("");
					System.out.println("Iteration halted: this looks linear.");
					okay = false;
				}
				else if(it > 20)
				{  
					System.out.println("");
					System.out.println("Iterations halted after 20 with no convergence.");
					okay = false;
				}
				else it = it + 1;
			}

			if(done)
			{
				System.out.println("");
				System.out.println("Convergence after "+it+"iterations.");
			}
 
			if(!okay)done = true;
		}

		for(i = 0 ; i <= V1-1 ; i++)
		{
			A[i] = -cf[i];
			B[i] = -cf[i+V1]*2;
			C[i] = -cf[i+V2];
		}
		D = 4.0*cf[W-1];

		for(i = 0 ; i <= W-1 ; i++)
			for(j = 0 ; j <= W-1 ; j++)
				rg[i][j] = rg[i][j]/FN;
		for(i = 0 ; i <= W-1 ; i++)
		{
			for(j = 0 ; j <= V1-1 ; j++)
			{
				rg[i][j+V1] = 2*rg[i][j+V1];
				rg[j+V1][i] = 2*rg[j+V1][i];
			}
			rg[i][W-1] = -4*rg[i][W-1];
			rg[W-1][i] = -4*rg[W-1][i];
		}
		return(okay);
	}

	/*　パラメータからt-統計量やraw cofficientを求める　*/
	public void MLstats(boolean okay)
	{
		int i,j,k;
    
		RA[NV-1]=A[NV-1];
		RB[NV-1]=B[NV-1];
		RC[NV-1]=C[NV-1];
		if(V>0)
			for(i=0;i<V;i++)
		{
						 RA[i]=A[i]/sd[i];
						 RB[i]=B[i]/sd[i];
						 RC[i]=C[i]/sd[i];
						 RA[NV-1]=RA[NV-1]-RA[i]*av[i];
						 RB[NV-1]=RB[NV-1]-RB[i]*av[i];
						 RC[NV-1]=RC[NV-1]-RC[i]*av[i];
					 }
		Yav=av[NV-1];
		Ysd=sd[NV-1];
		for(i=0;i<NV;i++)
		{
			RA[i]=RA[i]/Ysd;
			RB[i]=RB[i]/(Ysd*Ysd);
			RC[i]=RC[i]*Ysd;
		}
		RC[NV-1]=RC[NV-1] + Yav;
		RD=D/(Ysd*Ysd*Ysd*Ysd);

		for(i=0;i<W;i++)
			se[i]=Math.sqrt(rg[i][i]);
		for(i=0;i<NV;i++)
		{
			TA[i]=A[i]/se[i];
			TB[i]=B[i]/se[i+V1];
			TC[i]=C[i]/se[i+V2];
		}
		CH =2.0*(L2-L1);
		DF1=2+2*V;
		TD =D/se[W-1];
		DF2=FN-W+1;

		for(i=0;i<W;i++)
			for(j=0;j<W;j++)
				rg[i][j]=rg[i][j]/(se[i]*se[j]);
	}

	/*　カスプ面解析表示　*/
	public void paint(Graphics g)
	{
		setSize(900,650);
		g.drawString("Model:  0 = Alpha + Beta*(Y-Gamma) - Delta*(Y-Gamma)^3.",15,45);
		g.drawString("The conditional density of Y given X[1],...,X[v]:",15,60);
		g.drawString(" f(Y|X) = exp[ Psi + Alpha*Z + Beta*Z^2/2 - Delta*Z^4/4 ],",15,75);
		g.drawString("where Z = Y - gamma,",15,90);
		g.drawString("    Psi = constant (with respect to Y),",15,105);
		g.drawString("  Alpha = A[0] + A[1]*X[1] + ... + A[v]*X[v],",15,120);
		g.drawString("   Beta = B[0] + B[1]*X[1] + ... + B[v]*X[v],",15,135);
		g.drawString("  Gamma = C[0] + C[1]*X[1] + ... + C[v]*X[v],",15,150);
		g.drawString("  and v = "+V+" (in this analysis).",15,165);
		if(okay)
			g.drawString("Maximum Likelihood Estimation for the Cusp Model:",15,180);
		else
		{
			g.drawString("Due to convergence problems, the following results may",15,180);
			g.drawString("not be valid, and are given for diagnostic purposes only.",15,195);
		}
		g.drawString("Cases="+FN,15,210);
		g.drawString("Log-Likelihood="+(double)(int)(L2*1000)/1000,15,225);
		g.drawString("Standard coefficients, with t-statistics in parentheses:",15,240);
		g.drawString("Var",15,255);
		g.drawString("Alpha",90,255);
		g.drawString("Beta",195,255);
		g.drawString("Gamma",300,255);
		g.drawString("Delta",405,255);
		g.drawLine(15,260,480,260);
		g.drawString("Const",15,280);
		g.drawString(""+(double)(int)(A[NV-1]*1000)/1000,60,280);
		g.drawString("("+(double)(int)(TA[NV-1]*1000)/1000+")",105,280);
		g.drawString(""+(double)(int)(B[NV-1]*1000)/1000,165,280);
		g.drawString("("+(double)(int)(TB[NV-1]*1000)/1000+")",210,280);	
		g.drawString(""+(double)(int)(C[NV-1]*1000)/1000,270,280);
		g.drawString("("+(double)(int)(TC[NV-1]*1000)/1000+")",315,280);
		g.drawString(""+(double)(int)(D*1000)/1000,375,280);
		g.drawString("("+(double)(int)(TD*1000)/1000+")",420,280);
		if(V>0)
			for(int i=0;i<V;i++)
			{
				g.drawString(""+(i+1),15,280+15*(i+1));
				g.drawString(""+(double)(int)(A[i]*1000)/1000,60,280+15*(i+1));
				g.drawString("("+(double)(int)(TA[i]*1000)/1000+")",105,280+15*(i+1));
				g.drawString(""+(double)(int)(B[i]*1000)/1000,165,280+15*(i+1));
				g.drawString("("+(double)(int)(TB[i]*1000)/1000+")",210,280+15*(i+1));	
				g.drawString(""+(double)(int)(C[i]*1000)/1000,270,280+15*(i+1));
				g.drawString("("+(double)(int)(TC[i]*1000)/1000+")",315,280+15*(i+1));
			}
		g.drawString("(Each t-statistic has"+DF2+"degrees of freedom)",515,225);
		g.drawString("Raw coefficients:",515,240);
		g.drawString("Var",515,255);
		g.drawString("Alpha",560,255);
		g.drawString("Beta",640,255);
		g.drawString("Gamma",720,255);
		g.drawString("Delta",800,255);
		g.drawLine(515,260,840,260);
		g.drawString("Const",515,280);
		g.drawString(""+(double)(int)(RA[NV-1]*1000000)/1000000,560,280);
		g.drawString(""+(double)(int)(RB[NV-1]*1000000)/1000000,640,280);	
		g.drawString(""+(double)(int)(RC[NV-1]*1000000)/1000000,720,280);
		g.drawString(""+(double)(int)(RD*1000000)/1000000,800,280);
		if(V>0)
			for(int i=0;i<V;i++)
			{
				g.drawString(""+(i+1),515,280+(i+1)*15);
				g.drawString(""+(double)(int)(RA[i]*1000000)/1000000,560,280+(i+1)*15);
				g.drawString(""+(double)(int)(RB[i]*1000000)/1000000,640,280+(i+1)*15);
				g.drawString(""+(double)(int)(RC[i]*1000000)/1000000,720,280+(i+1)*15);
			}
		if(V>0)
		{
			g.drawString("Test for H0:  Conditional densities are Type N2 (linear regression)",15,420);
			g.drawString("  versus H1:  Conditional densities are Type N4 (cusp regression)",15,435);
		}
		else
		{
			g.drawString("Test for H0:  Density is Type N2 (normal)",15,420);
			g.drawString("  versus H1:  Density is Type N4 (cusp)",15,435);
		}
		g.drawString("  >>>>>> Asymptotic Chi-square="+ (double)(int)(CH*1000)/1000+"  (df ="+DF1+") <<<<<<",15,450);
		g.drawString("Test for H0:  Delta = 0  (i.e. no cubic term)",515,420);
		g.drawString("  versus H1:  Delta > 0  (a one-tailed test)",515,435);
		g.drawString("  >>>>>> t="+(double)(int)(TD*1000)/1000+"  (df ="+DF2+") <<<<<<",515,450);
		if(V1>0)
		{
			g.drawString("Estimated correlations between Alpha and Gamma estimators:",15,480);
			for(int i=0;i<V1;i++)
				g.drawString("G"+i,70+60*i,495);
			g.drawLine(15,500,100+60*V1,500);
			for(int i=0;i<V1;i++)
			{
				g.drawString("A"+i,15,520+15*i);
				for(int j=0;j<V1;j++)
					g.drawString(""+(double)(int)(rg[i][j+V2]*1000)/1000,60+60*j,520+15*i);
			}
		}
	}
}