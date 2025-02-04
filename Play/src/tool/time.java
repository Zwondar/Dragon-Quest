package tool;

import frame.Startgame;
import image.getimage;

public class time implements Runnable{
	
	public static int curWarriorhp=0;
	
	public static int curMonsterhp=0;
	
	public static int CurMonster=0;
	
	public static int CurSlime=0;
	
	public static int CurMonster2=0;
	
	public static int CurMonster3=0;
	
	static Thread thread;

	@Override
	public void run() {
		
		while(true) {
			if(CurMonster>=10&&Obstale.treeHP>0&&Startgame.checkpoint==1) {
				getimage.monster.add(new Monster(Startgame.width*3));
				CurMonster=0;
			}
			
			if(backgrand.showpicture==getimage.backgrandthree&&CurSlime>=5&&Obstale.treeHP>0&&Startgame.checkpoint==1) {
				getimage.slime.add(new Slime(Startgame.width*2));
				System.out.println(2);
				CurSlime=0;
			}
			if(backgrand.showpicture==getimage.backgrandfour&&CurMonster2>=5&&Obstale.treeHP>0&&Startgame.checkpoint==1) {
				getimage.green.add(new Monster2(Startgame.width*2));
				CurMonster2=0;
			}
			
			if(backgrand.showpicture==getimage.backgrandtwo&&CurMonster3>=5&&Obstale.treeHP>0&&Startgame.checkpoint==1) {
				getimage.red.add(new Monster3(Startgame.width*2));
				
				CurMonster3=0;
			}
			
			CurMonster2++;
			CurMonster3++;
			CurSlime++;
			CurMonster++;
			curWarriorhp++;
			curMonsterhp++;
			if(curMonsterhp==5) {
				
				for(int i=0;i<getimage.monster.size();i++) {
					if(getimage.monster.get(i).MinsterHp<=Monster.HP&&Obstale.treeHP>0&&getimage.monster.get(i).MinsterHp>0) getimage.monster.get(i).MinsterHp++;			
					}
				curMonsterhp=0;
			}
			
			if(curWarriorhp==3) {
				if(Warrior.WarriorHP<50&&Warrior.WarriorHP>0)Warrior.WarriorHP++;
				if(Warrior.WarriorMana<20) Warrior.WarriorMana++;
				curWarriorhp=0;
			}
			
			try {
				thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		
	}
	
	}
	public void play() {
		thread=new Thread(this);
		thread.start();
	}
}
