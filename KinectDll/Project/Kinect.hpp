#pragma once
#include "MSR_NuiApi.h"
#include <fstream>
#include <iostream>

namespace Kinect {

	enum KinectDataType {
		DEPTH,
		VIDEO,
		SKELETON
	};

	int VIDEO_DATA_SIZE_BYTES = 640 * 480 * 4;
	int DEPTH_DATA_SIZE_BYTES = 320 * 240 * 2;

	// status flags
	bool tracking_skeleton = false;
	int tracked_skeleton_id;

	// latest frame holders
	BYTE* depth_data = NULL;
	BYTE* video_data = NULL;
	NUI_SKELETON_FRAME skeleton_frame;

	// skeleton frame holder
	BYTE* skeleton_data = NULL;

};

// event and callback handler stubs
void initialise_callbacks();
void video_event();
void depth_event();
void skeleton_event();

// input streams
HANDLE m_pDepthStreamHandle;
HANDLE m_pVideoStreamHandle;

// events
HANDLE hEvents[4];
HANDLE m_hNextDepthFrameEvent;
HANDLE m_hNextVideoFrameEvent;
HANDLE m_hNextSkeletonEvent;
HANDLE m_hEvNuiProcessStop;

namespace Kinect {

	// --- initialisation --- 
	HRESULT Init(){

		// Configure events to be listened on
		m_hNextDepthFrameEvent = CreateEvent( NULL, TRUE , FALSE, NULL );
		m_hNextVideoFrameEvent = CreateEvent( NULL, TRUE , FALSE, NULL );
		m_hNextSkeletonEvent   = CreateEvent( NULL, TRUE , FALSE, NULL );
		m_hEvNuiProcessStop    = CreateEvent( NULL, FALSE, FALSE, NULL );

		hEvents[0]=m_hEvNuiProcessStop;
		hEvents[1]=m_hNextDepthFrameEvent;
		hEvents[2]=m_hNextVideoFrameEvent;
		hEvents[3]=m_hNextSkeletonEvent;



		printf("Kinect::Init() -- events created\n");

		// initialisation
		HRESULT hr = NuiInitialize(NUI_INITIALIZE_FLAG_USES_DEPTH_AND_PLAYER_INDEX | NUI_INITIALIZE_FLAG_USES_SKELETON | NUI_INITIALIZE_FLAG_USES_COLOR);
		if(FAILED(hr)) {
			MessageBox(NULL,L"Kinect initialisation failed.",L"Kinect Error",NULL);
			return hr;
		}
		printf("Kinect::Init() -- done NuiInitialize\n");

		hr = NuiSkeletonTrackingEnable( m_hNextSkeletonEvent, 0 );
		if(FAILED(hr)) return hr;
		printf("Kinect::Init() -- done NuiSkeletonTrackingEnable\n");

		hr = NuiImageStreamOpen(
			NUI_IMAGE_TYPE_COLOR,
			NUI_IMAGE_RESOLUTION_640x480,
			0,
			2,
			m_hNextVideoFrameEvent,
			&m_pVideoStreamHandle );
		if(FAILED(hr)) return hr;
		printf("Kinect::Init() -- done NuiImageStreamOpen for video\n");

		hr = NuiImageStreamOpen(
			NUI_IMAGE_TYPE_DEPTH_AND_PLAYER_INDEX,
			NUI_IMAGE_RESOLUTION_320x240,
			0,
			2,
			m_hNextDepthFrameEvent,
			&m_pDepthStreamHandle );
		if(FAILED(hr)) return hr;
		printf("Kinect::Init() -- done NuiImageStreamOpen for depth\n");

		return hr;

	}

	// --- close down --- 
	void Close(void){

		NuiShutdown( );
		if( m_hNextSkeletonEvent && ( m_hNextSkeletonEvent != INVALID_HANDLE_VALUE ) )
		{
			CloseHandle( m_hNextSkeletonEvent );
			m_hNextSkeletonEvent = NULL;
		}
		if( m_hNextDepthFrameEvent && ( m_hNextDepthFrameEvent != INVALID_HANDLE_VALUE ) )
		{
			CloseHandle( m_hNextDepthFrameEvent );
			m_hNextDepthFrameEvent = NULL;
		}
		if( m_hNextVideoFrameEvent && ( m_hNextVideoFrameEvent != INVALID_HANDLE_VALUE ) )
		{
			CloseHandle( m_hNextVideoFrameEvent );
			m_hNextVideoFrameEvent = NULL;
		}


		// free buffers
		if(video_data != NULL)
			free(video_data);
		if(depth_data != NULL)
			free(depth_data);

	}

	void SetCameraAngle(int a){

		if(a>27) a = 27;
		if(a<-27) a = -27;
		NuiCameraElevationSetAngle(a);
	}

	int GetCameraAngle(){
		LONG angle;
		NuiCameraElevationGetAngle(&angle);
		return (int)angle;
	}

	void SaveData(KinectDataType t, wchar_t* file_name){

		std::fstream ofs( file_name, std::ios::out | std::ios::binary );
		switch(t){
		case DEPTH:
			ofs.write( (const char*) depth_data, Kinect::DEPTH_DATA_SIZE_BYTES );break;
		case VIDEO:
			ofs.write( (const char*) video_data, Kinect::VIDEO_DATA_SIZE_BYTES );break;
		case SKELETON:
			ofs.write( (const char*) &skeleton_frame, sizeof(NUI_SKELETON_FRAME));break;
		}
		ofs.close();
	}

	void LoadData(KinectDataType t, wchar_t* file_name){

		std::fstream ifs( file_name , std::ios::in | std::ios::binary );

		if(!ifs.is_open()) {
			wchar_t wStr[255]; 
			swprintf(wStr, 255, L"Kinect::LoadData can't open file: %s", file_name); 
			MessageBox(NULL,wStr,L"Kinect::LoadData Error",0);
			return;
		}
		switch(t){
		case DEPTH:
			ifs.read( (char*) depth_data, Kinect::DEPTH_DATA_SIZE_BYTES );break;
		case VIDEO:
			ifs.read( (char*) video_data, Kinect::VIDEO_DATA_SIZE_BYTES );break;
		case SKELETON:
			ifs.read( (char*) &skeleton_frame, sizeof(NUI_SKELETON_FRAME) );break;
		}

		ifs.close();

	}


};
