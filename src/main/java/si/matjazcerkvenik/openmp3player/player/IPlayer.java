package si.matjazcerkvenik.openmp3player.player;

/**
 * Implementation of <code>IPlayer</code> must implement basic functions of every music player: 
 * play, stop, pause, resume.
 * 
 * @author matjaz
 *
 */
public interface IPlayer {
	
	public void	play(String filepath);
	
	public void	stop();
	
	public void	pause();
	
	public void resume();
	
	public PlayerStatus getStatus();
	
}
