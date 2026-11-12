package tool;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import Sound.soundplayer;
import frame.Startgame;
import image.getimage;
import netWork.netframe;

public class MyKeyListner implements KeyListener{
	
	static Skill skill=new Skill();
	
	@Override
	public void keyTyped(KeyEvent e) {
    char c=e.getKeyChar();
    if(Startgame.checkpoint!=0) {
    	
    	if(c==' ') {//如果按下空格
    		
        	Startgame.jumpstatus="jump";
        }
    	// 2026-11-09 修复（优先级6）：只有在 Skill.play() 成功开始施法后才扣蓝。
    	// 原代码先 play() 再无条件扣蓝，按住技能键时按键重复会创建大量线程并重复扣蓝。
    	if(c=='u'&&Warrior.WarriorMana>=5) {
    		Skill.Skillstatus="Revitalize";
    		if(skill.play()) Warrior.WarriorMana-=5;
    	}

    	if(c=='o'&&Warrior.WarriorMana>=10) {
    		Skill.Skillstatus="Sword";
    		if(skill.play()) Warrior.WarriorMana-=10;
    	}

    	if(c=='i'&&Warrior.WarriorMana>=10) {
    		Skill.Skillstatus="stone";
    		if(skill.play()) Warrior.WarriorMana-=10;
    	}
    	
    }
   
}

	@Override
	public void keyPressed(KeyEvent e) {
		
	int code =e.getKeyCode();
	
	if(Startgame.checkpoint==0) {//开始界面
		
		switch(code) {
		
		case KeyEvent.VK_UP:
			
			if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17) backgrand.y=Startgame.height/4+Startgame.height/6-Startgame.height/17+Startgame.height/10*5;
			
			backgrand.y-=Startgame.height/10;
			
			break;
			
		case KeyEvent.VK_DOWN:
			
			if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17+Startgame.height/10*4) backgrand.y=Startgame.height/4+Startgame.height/6-Startgame.height/17-Startgame.height/10;
			
			backgrand.y+=Startgame.height/10;
			
			break;
			
       case KeyEvent.VK_ENTER:
    	   
    	   soundplayer.enter();
    	   
			if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17)  {
				Startgame.checkpoint=1;
				// 2026-11-09 修复（优先级6）：关卡选择可能先于难度选择创建怪物，
				// 进入游戏时把已存在怪物的血量同步为当前难度的上限。
				applyDifficultyToExistingEntities();
			}
			
			else if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17+Startgame.height/10) {
				Startgame.checkpoint=-2;
				backgrand.y=backgrand.fonty-Startgame.height/17;
			}
			
			else if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17+Startgame.height/10*2) {
				Startgame.checkpoint=-1;
				backgrand.y=Startgame.height/5;
			}
			
			else if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17+Startgame.height/10*3) 
				
				Startgame.netcontrol=true;
			
			else if(backgrand.y==Startgame.height/4+Startgame.height/6-Startgame.height/17+Startgame.height/10*4) System.exit(0);
			break;
		}
		
	}	
	
	if(Startgame.checkpoint==-1) {
switch(code) {
		
		case KeyEvent.VK_UP :
			if(backgrand.y==Startgame.height/5) backgrand.y=Startgame.height/5+Startgame.height/3*3;
			backgrand.y-=Startgame.height/3;
			
			break;
			
		case KeyEvent.VK_DOWN :
			if(backgrand.y==Startgame.height/5+Startgame.height/3*2) backgrand.y=Startgame.height/5-Startgame.height/3;
			backgrand.y+=Startgame.height/3;
		
			break;
			
       case KeyEvent.VK_ENTER :
    	   
    	   soundplayer.enter();
    	   
    	   if(backgrand.y==Startgame.height/5) { 
    		   
    		   backgrand.showpicture=getimage.backgrandtwo;
    		   
    		   getimage.red.add(new Monster3(Startgame.width));
    	   
    	   }
    	   
    	   else if(backgrand.y==Startgame.height/5+Startgame.height/3) {
    		   backgrand.showpicture=getimage.backgrandthree;
    		   getimage.slime.add(new Slime(Startgame.width));
    	   }
    	   
    	   else if(backgrand.y==Startgame.height/5+Startgame.height/3*2) {
    		   backgrand.showpicture=getimage.backgrandfour;
    		   getimage.green.add(new Monster2(Startgame.width));
    	   }
		
			break;
			
       case KeyEvent.VK_ESCAPE :
    	   
    	   soundplayer.choose();
    	   
    	   Startgame.checkpoint=0;
    	   
    	   backgrand.y=Startgame.height/4+Startgame.height/6-Startgame.height/17;
    	   
    	   break;
       	
		}
		
	}
	
	if(Startgame.checkpoint==-2) {
		switch(code) {
		case KeyEvent.VK_UP:
			if(backgrand.y==backgrand.fonty-Startgame.height/17) backgrand.y=backgrand.fonty-Startgame.height/17+Startgame.height/10*3;
			backgrand.y-=Startgame.height/10;
			break;
		case KeyEvent.VK_DOWN:
			if(backgrand.y==backgrand.fonty-Startgame.height/17+Startgame.height/10*2) backgrand.y=backgrand.fonty-Startgame.height/17-Startgame.height/10;
				backgrand.y+=Startgame.height/10;
				break;
		case KeyEvent.VK_ENTER:
			
			soundplayer.enter();
			
			if(backgrand.y==backgrand.fonty-Startgame.height/17) {

				// 2026-11-09 修复（优先级6）：记录难度，供重置时使用。
				Startgame.difficulty=1;
				Monster.HP=5;
				Slime.HP=3;
				Monster2.HP=3;
				Monster3.HP=3;
				Obstale.treeHP=7;
				Warrior.WarriorHP=45;
				applyDifficultyToExistingEntities();

			}else if(backgrand.y==backgrand.fonty-Startgame.height/17+Startgame.height/10) {
				Startgame.difficulty=2;
				Monster.HP=8;
				Slime.HP=5;
				Monster2.HP=5;
				Monster3.HP=5;
				Obstale.treeHP=15;
				Warrior.WarriorHP=35;
				applyDifficultyToExistingEntities();

			}else if(backgrand.y==backgrand.fonty-Startgame.height/17+Startgame.height/10*2) {
				Startgame.difficulty=3;
				Monster.HP=12;
				Slime.HP=7;
				Monster2.HP=7;
				Monster3.HP=7;
				Obstale.treeHP=20;
				Warrior.WarriorHP=15;
				applyDifficultyToExistingEntities();

			}
			break;
		case KeyEvent.VK_ESCAPE:
			
			soundplayer.choose();
			
			Startgame.checkpoint=0;
			backgrand.y=Startgame.height/4+Startgame.height/6-Startgame.height/17;
			break;
		}
	}
	
	if(Startgame.checkpoint ==1) {
		
		if(code==KeyEvent.VK_J) { 
			
			Startgame.attackstatus="attack";
			
		}
		
		switch(code) {
		
		case KeyEvent.VK_D:
			
			Startgame.status="right";
			// 2026-11-09 修复（优先级6）：记录朝向，供攻击动画选择左右帧、结束后恢复站立帧。
			Warrior.facing="right";
			break;
			
		case KeyEvent.VK_A:
			
			Startgame.status="left";
			Warrior.facing="left";
			
			break;
			
		   }
		
	  }
		
}

	/**
	 * 2026-11-09 新增（优先级6）：难度修改后同步已存在怪物的血量上限，
	 * 避免"先选关卡再选难度"时旧怪物仍使用旧数值。遍历使用线程安全集合。
	 */
	private static void applyDifficultyToExistingEntities() {
		for (Monster m : getimage.monster) m.MinsterHp = Monster.HP;
		for (Slime s : getimage.slime) s.SlimeHp = Slime.HP;
		for (Monster3 m : getimage.red) m.MinsterHp = Monster3.HP;
		for (Monster2 m : getimage.green) m.MinsterHp = Monster2.HP;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code =e.getKeyCode();
		
		if(Startgame.checkpoint !=0) {
			
			if(code==KeyEvent.VK_J) {
				
				Startgame.attackstatus="stand";
				
			}
			
			switch(code) {
			
			case KeyEvent.VK_D:
				
				Startgame.status="stand";
				
				break;
				
			case KeyEvent.VK_A:
				
				Startgame.status="stand";
				
				break;
				
			}
			
		}
		
	}
	
}
