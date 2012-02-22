#pragma once
#include <Windows.h>
#include "Kinect.hpp"
#include "jni.h"
#include <iostream>


// JNI interface
extern "C" {

	JNIEXPORT jbyteArray Java_kinect_Kinect_AllocateVideo(JNIEnv *env, jclass cls) {
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );

		Kinect::video_data = (BYTE*)malloc(Kinect::VIDEO_DATA_SIZE_BYTES);
		return (jbyteArray)env->NewDirectByteBuffer(Kinect::video_data,Kinect::VIDEO_DATA_SIZE_BYTES);

	}

	JNIEXPORT jbyteArray Java_kinect_Kinect_AllocateDepth(JNIEnv *env, jclass cls) {
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );

		Kinect::depth_data = (BYTE*)malloc(Kinect::DEPTH_DATA_SIZE_BYTES);
		return (jbyteArray)env->NewDirectByteBuffer(Kinect::depth_data,Kinect::DEPTH_DATA_SIZE_BYTES);

	}

	JNIEXPORT bool JNICALL Java_kinect_Kinect_Start(JNIEnv *env, jclass cls) {
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );

		if(FAILED(Kinect::Init()))
			return false;

		return true;

	}

	JNIEXPORT void JNICALL Java_kinect_Kinect_Stop(JNIEnv *env, jclass cls) {
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		Kinect::Close();

	}


	JNIEXPORT jint JNICALL Java_kinect_Kinect_GetNextEvent(JNIEnv *env, jclass cls){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );

		int    nEventIdx;
		nEventIdx=WaitForMultipleObjects(sizeof(hEvents)/sizeof(hEvents[0]),hEvents,FALSE,100);

		// Process signal events
		switch(nEventIdx)
		{
		case 0:break;
		case 1:depth_event();break;
		case 2:video_event();break;
		case 3:skeleton_event( );break;
		}

		return (jint)nEventIdx;

	}

	JNIEXPORT jint JNICALL Java_kinect_Kinect_GetTrackedSkeletonId(JNIEnv *env, jclass cls){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		return Kinect::tracked_skeleton_id;
	}


	JNIEXPORT jboolean JNICALL Java_kinect_Kinect_IsTrackingSkeleton(JNIEnv *env, jclass cls){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		return (jboolean)Kinect::tracking_skeleton;
	}

	JNIEXPORT jint JNICALL Java_kinect_Kinect_GetSkeletonTrackingState(JNIEnv *env, jclass cls,jint SkeletonID){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		return Kinect::skeleton_frame.SkeletonData[SkeletonID].eTrackingState;
	}

	JNIEXPORT jint JNICALL Java_kinect_Kinect_GetJointTrackingState(JNIEnv *env, jclass cls,jint SkeletonID, jint JointID){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		return Kinect::skeleton_frame.SkeletonData[SkeletonID].eSkeletonPositionTrackingState[JointID];
	}

	JNIEXPORT jfloat JNICALL Java_kinect_Kinect_GetJointPositionByIndex(JNIEnv *env, jclass cls,jint SkeletonID, jint JointID, jint PositionIndex){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		if(PositionIndex == 0) return Kinect::skeleton_frame.SkeletonData[SkeletonID].SkeletonPositions[JointID].x;
		if(PositionIndex == 1) return Kinect::skeleton_frame.SkeletonData[SkeletonID].SkeletonPositions[JointID].y;
		if(PositionIndex == 2) return Kinect::skeleton_frame.SkeletonData[SkeletonID].SkeletonPositions[JointID].z;
		return 0;
	}

	JNIEXPORT jfloat JNICALL Java_kinect_Kinect_GetSkeletonNormalToGravityByIndex(JNIEnv *env, jclass cls, jint PositionIndex){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		if(PositionIndex == 0) return Kinect::skeleton_frame.vNormalToGravity.x;
		if(PositionIndex == 1) return Kinect::skeleton_frame.vNormalToGravity.y;
		if(PositionIndex == 2) return Kinect::skeleton_frame.vNormalToGravity.z;
		return 0;
	}

	JNIEXPORT jfloat JNICALL Java_kinect_Kinect_GetSkeletonFloorClipPlaneByIndex(JNIEnv *env, jclass cls, jint PositionIndex){
		UNREFERENCED_PARAMETER( env );
		UNREFERENCED_PARAMETER( cls );
		if(PositionIndex == 0) return Kinect::skeleton_frame.vFloorClipPlane.x;
		if(PositionIndex == 1) return Kinect::skeleton_frame.vFloorClipPlane.y;
		if(PositionIndex == 2) return Kinect::skeleton_frame.vFloorClipPlane.z;
		return 0;
	}




}

// event handlers
void video_event(void){

	const NUI_IMAGE_FRAME * pImageFrame = NULL;
	HRESULT hr = NuiImageStreamGetNextFrame(m_pVideoStreamHandle,0,&pImageFrame );  // get the next frame if available
	if( SUCCEEDED( hr ) )												
	{
		INuiFrameTexture * pTexture = pImageFrame->pFrameTexture;
		NUI_LOCKED_RECT LockedRect;
		pTexture->LockRect( 0, &LockedRect, NULL, 0 );
		if( LockedRect.Pitch != 0 )
		{
			byte * pBuffer = (byte*) LockedRect.pBits;

			// store video information
			memcpy(Kinect::video_data,pBuffer,Kinect::VIDEO_DATA_SIZE_BYTES);

		}
		else
		{
			OutputDebugString( L"buffer length of received video is bogus\r\n");
		}
		NuiImageStreamReleaseFrame( m_pVideoStreamHandle, pImageFrame );            // release the resources

	}

}

void depth_event(void) {

	const NUI_IMAGE_FRAME * pImageFrame = NULL;
	HRESULT hr = NuiImageStreamGetNextFrame(m_pDepthStreamHandle,0,&pImageFrame);   // get the next frame if available
	if( SUCCEEDED( hr ) ) 
	{
		INuiFrameTexture * pTexture = pImageFrame->pFrameTexture;
		NUI_LOCKED_RECT LockedRect;
		pTexture->LockRect( 0, &LockedRect, NULL, 0 );
		BYTE * pBuffer = (BYTE*) LockedRect.pBits;
		if( LockedRect.Pitch != 0 )
		{

			// store depth information
			memcpy(Kinect::depth_data,pBuffer,Kinect::DEPTH_DATA_SIZE_BYTES);

		}
		else
		{
			OutputDebugString( L"Buffer length of received depth is bogus\r\n" );
		}
		NuiImageStreamReleaseFrame( m_pDepthStreamHandle, pImageFrame );			// release the resources

	}

}

void skeleton_event(void){

	NuiSkeletonGetNextFrame( 0, &Kinect::skeleton_frame);
	for( int i = 0 ; i < NUI_SKELETON_COUNT ; i++ )
	{
		if( Kinect::skeleton_frame.SkeletonData[i].eTrackingState == NUI_SKELETON_TRACKED ){
			Kinect::tracking_skeleton = true;
			Kinect::tracked_skeleton_id = i;
			break;
		}
	}

}

