package com.partysun.cats.pool;

import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.util.pool.GenericPool;

import com.partysun.cats.entities.Artifact;

/**
 * Создаем Пул Артефактов, который содержит все нужные объекты артефактов сцены.
 * Если Артефакт нужен и нету ни одного свободного объекта в пуле то создается 
 * объект Артефакта и используется.
 * Если он стал не нужен его можно отправить в пул на ожидание использования.
 * Таким образом мы создаем только столько объектов сколько действительно 
 * необходимо для игровой сцены.
 * @describe 
 * obtainPoolItem - Получить объект из пула для использования
 * reciclePoolItem - Отправить объект в пул на хранение
 * @author Yura Zatsepin *
 */
public class ArtifactPool extends GenericPool <Artifact> {
	private static ArtifactPool artifactPool;
	private TiledTextureRegion mTextureRegion;
	 
	public static ArtifactPool getInstance() {
		if (artifactPool == null)
			artifactPool = new ArtifactPool();
		return artifactPool;
	}

	/**
	 * Устанавливаем TiledTextureRegion для Артефактов.
	 * @param pTextureRegion
	 * @return
	 */
	public ArtifactPool setArtifactTexture(TiledTextureRegion pTextureRegion) {
		if (pTextureRegion == null) {
			throw new IllegalArgumentException(
					"The texture region must not be NULL");
		}
		mTextureRegion = pTextureRegion;
		return this;
	}

	/**
	 * Called when a Artifact is required but there isn't one in the pool
	 * Вызываем когда требуется Артефакт, но ни одного нету в пуле.
	 */
	@Override
	protected Artifact onAllocatePoolItem() {
		Artifact pArtifact = new Artifact(mTextureRegion);		
		return pArtifact;
	}

	/**
	 * Called when a Artifact is sent to the pool
	 * Вызываем когда Артефакт посылаем в пул
	 */
	@Override
	protected void onHandleRecycleItem(final Artifact pArtifact) {
		pArtifact.setIgnoreUpdate(true);
		pArtifact.setVisible(false);
		pArtifact.setPosition(0, 0);
	}

	/**
	 * &nbsp;* Called just before a Artifact is returned to the caller, this is
	 * where you write your initialize code &nbsp;* i.e. set location, rotation,
	 * etc.
	 * Вызываем метод когда объект Артифакт возвращается из пула. Можно 
	 * с ним провести инициализацию и изменение позиции и др.
	 */
	@Override
	protected void onHandleObtainItem(final Artifact pArtifact) {	
		pArtifact.reset();	
	}

	public void clear() {
		artifactPool=null;
	}
}
