import React, { useRef, useEffect } from 'react';

export default function Camera({isVideoStart}) {
  const videoRef = useRef(null);
  const canvasRef = useRef(null);

  useEffect(() => {
    getVideo();
  });

  const getVideo = async () => {
    try {
        if (isVideoStart) {
            const stream = await navigator.mediaDevices.getUserMedia({
                video: true,
            });
            const video = videoRef.current;
            if (video) {
                video.srcObject = stream;
                video.play();
            }
        }
        } catch (err) {
        console.error(err);
        }
  };

const getCapture = () => {
    const canvas = canvasRef.current;
    const video = videoRef.current;

    if (canvas && video) {
        const context = canvas.getContext('2d');
        context.drawImage(video, 0, 0, canvas.width, canvas.height);
        const dataUrl = canvas.toDataURL('image/png')
        console.log(dataUrl)
    }
};

  return (
    <div>
      <video ref={videoRef} />
      <button type="button" onClick={getCapture}> capture </button>
      {getCapture.dataUrl}
      <canvas ref={canvasRef} style={{display: 'none'}} />
    </div>
  );
}